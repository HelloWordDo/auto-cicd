package com.arkmon.autocicd.schedulers;

import Exceptions.MAException;
import ReturnEntities.ReturnData;
import com.arkmon.autocicd.configs.CustomsConfig;
import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import com.arkmon.autocicd.domains.dto.response.JenkinsJobDetailRspDTO;
import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;
import com.arkmon.autocicd.domains.model.JenkinsProcessHistory;
import com.arkmon.autocicd.domains.model.JenkinsServiceUrl;
import com.arkmon.autocicd.enums.EnvironmentEnum;
import com.arkmon.autocicd.enums.JenkinsProcessEnum;
import com.arkmon.autocicd.mappers.JenkinsProcessHistoryMapper;
import com.arkmon.autocicd.services.IntegrationTestService;
import com.arkmon.autocicd.services.JenkinsProcessService;
import com.arkmon.autocicd.services.NotificationService;
import com.arkmon.autocicd.services.ReleaseNoteManagementService;
import com.arkmon.autocicd.utils.EnumUtil;
import com.arkmon.autocicd.utils.HttpUtil;
import com.arkmon.autocicd.utils.JenkinsUtil;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import utils.JsonUtil;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

/**
 * 定时器，监控Jenkins任务的全局队列
 *
 * @author X.J
 * @date 2021/2/25
 */
@Component
@Slf4j
public class MonitorQueueScheduler {

    @Resource(name = "monitorBuildQueue")
    Queue<MonitorQueueItemEntity> q;

    @Resource(name = "monitorTagQueue")
    Queue<MonitorQueueItemEntity> p;

    @Autowired
    CustomsConfig cc;

    @Autowired
    IntegrationTestService its;

    @Autowired
    NotificationService ns;

    @Autowired
    JenkinsProcessService jps;

    @Resource(name = "obtainJenkinsServiceUrl")
    private Map<String, JenkinsServiceUrl> obtainJenkinsServiceUrl;

    @Autowired
    private ThreadPoolTaskExecutor customTaskExecutor;

    @Resource
    private JenkinsProcessHistoryMapper jphm;

    @Resource(name = "monitorWaitingMap")
    private Map<String, JenkinsProcessBuildReqDTO> monitorWaitingMap;

    @Scheduled(fixedDelayString = "10000")
    public void serviceBuild() {
        if (q.size() != 0) {
            log.info("当前监听的服务构建任务: {}", q.toString());
        }
        if (q.isEmpty()) {
            return;
        }
        Long ts = System.currentTimeMillis();
        for (MonitorQueueItemEntity m : q) {
            String projectName = m.getJenkinsProcessBuildReqDTO().getProjectName();
            String environment = m.getJenkinsProcessBuildReqDTO().getEnvironment();
            String serviceName = m.getJenkinsProcessBuildReqDTO().getServiceName();
            if (Math.abs(ts - m.getTs()) > 1000 * 60 * 10) {
                q.remove(m);
                updateTaskStatusAsync(m, JenkinsProcessEnum.TIMEOUT.getName().concat("@").concat(environment));
                continue;
            }
            JenkinsServiceUrl jenkinsServiceUrl = obtainJenkinsServiceUrl.get(String.format("%s-%s-%s", projectName, environment, serviceName));
            if (jenkinsServiceUrl == null) {
                log.error("部署环境不支持");
                return;
            }
            String buildBaseUrl = String.format(jenkinsServiceUrl.getBaseUrl(), cc.getJenkins().getUser(), cc.getJenkins().getToken());

            String jenkinsJobDetailUrl = buildBaseUrl + "/api/json";
            String[] jobDetailCmd = {"curl", "-X", "POST", jenkinsJobDetailUrl};
            String ret = HttpUtil.execCurl(jobDetailCmd);
            JenkinsJobDetailRspDTO jenkinsJobDetailRspDTO;
            try {
                jenkinsJobDetailRspDTO = JsonUtil.jsonStr2Obj(ret, JenkinsJobDetailRspDTO.class);
            } catch (Exception e) {
                log.error("{}", ret);
                continue;
            }
            // 判断本次构建是否成功
            Optional.ofNullable(jenkinsJobDetailRspDTO).orElseThrow(() -> new MAException("jenkinsJobDetailRspDTO返回为空"));
            int crtBuildNum = jenkinsJobDetailRspDTO.getNextBuildNumber() -1;
            // 表示本次构建已触发
            if (crtBuildNum > m.getLastBuild()) {
                int lastSuccessBuildNum = Optional.ofNullable(jenkinsJobDetailRspDTO.getLastSuccessfulBuild()).map(item -> item.getNumber()).orElse(-1);
                int lastUnsuccessBuildNum = Optional.ofNullable(jenkinsJobDetailRspDTO.getLastUnsuccessfulBuild()).map(item -> item.getNumber()).orElse(-1);
                int lastFailedBuildNum = Optional.ofNullable(jenkinsJobDetailRspDTO.getLastFailedBuild()).map(item -> item.getNumber()).orElse(-1);
                if (lastSuccessBuildNum == crtBuildNum) {
                    // 表示本次构建完成且成功
                    log.info("构建成功....启动集成测试...");
                    updateTaskStatusAsync(m, JenkinsProcessEnum.SUCCESS.getName().concat("@").concat(environment));
                    q.remove(m);
                    String consoleUrl = buildBaseUrl.concat("/").concat(String.valueOf(crtBuildNum)).concat("/consoleText");
                    String[] consoleCmd = {"curl", "-X", "POST", consoleUrl};
                    String consoleStr = HttpUtil.execCurl(consoleCmd);
                    m.setCommitId(obtainCommitId(consoleStr));
                    m.setSonarqubeUrl(obtainSonarqube(consoleStr));
                    its.start(m);
                } else if (lastFailedBuildNum == crtBuildNum) {
                    // 表示本次构建完成且失败, 失败原因是构建中某个stage出错
                    log.info("构建失败，失败原因是构建中某个stage出错");
                    updateTaskStatusAsync(m, JenkinsProcessEnum.FAILURE.getName());
                    q.remove(m);
                    ns.notifyByEmail(m, "构建失败，失败原因是构建中某个stage出错");
                    ns.notifyByDingTalk(m, "构建失败，失败原因是构建中某个stage出错");
                } else if (lastUnsuccessBuildNum == crtBuildNum) {
                    // 表示本次构建完成且失败, 失败原因是被外界终止构建，如用户取消
                    log.info("构建失败，失败原因是被外界终止构建，如用户取消");
                    updateTaskStatusAsync(m, JenkinsProcessEnum.TERMINATION.getName());
                    q.remove(m);
                    ns.notifyByEmail(m, "构建失败，失败原因是被外界终止构建，如用户取消");
                    ns.notifyByDingTalk(m, "构建失败，失败原因是被外界终止构建，如用户取消");
                }
            }
        }
    }

    @Scheduled(fixedDelayString = "5000")
    public void tagBuild() {
        if (p.size() != 0) {
            log.info("当前监听的Tag构建任务: {}", p.toString());
        }
        if (p.isEmpty()) {
            return;
        }
        Long ts = System.currentTimeMillis();
        for (MonitorQueueItemEntity m : p) {
            if (Math.abs(ts - m.getTs()) > 1000 * 60 * 5) {
                p.remove(m);
                continue;
            }
            String jenkinsTagDetailUrl = String.format(m.getTagBaseUrl()) + String.format("/%s/consoleText", m.getLastBuild() + 1);
            String ret = JenkinsUtil.callJenkinsApi(jenkinsTagDetailUrl);
            if (!ret.contains(cc.getJenkins().getUser())) {
                continue;
            }
            //通过jenkins指定的用户名进行构建
            if (ret.contains("Finished: SUCCESS")) {
                // 打 Tag 成功, 判断下一步是 UAT 部署还是 PROD 部署
                log.info("创建Tag成功");
                String lastSuccessBuildEnvironment = m.getJenkinsProcessBuildReqDTO().getEnvironment();
                EnvironmentEnum lastEe = EnumUtil.getEnumByParameter(EnvironmentEnum.class, lastSuccessBuildEnvironment);
                if (lastEe == null) {
                    log.error("在获取部署环境配置枚举时出错");
                    ns.notifyByDingTalk(m, "在获取部署环境配置枚举时出错");
                    p.remove(m);
                    continue;
                }
                if (lastEe.getName().equals(m.getOriginalEnvironment())) {
                    log.info("原始请求的最终部署环境{}与当前已成功的部署环境{}相同，终止CICD", m.getOriginalEnvironment(), lastEe.getName());
                    ns.notifyByDingTalk(m, lastEe.getName() + "环境部署成功，集成测试成功");
                    p.remove(m);
                    continue;
                }
                if (lastEe.getCode() == EnvironmentEnum.DEPLOY_PROD.getCode()) {
                    log.info("当前环境为生产环境，无需下一步操作");
                    ns.notifyByDingTalk(m, "PROD环境部署成功，集成测试成功");
                    p.remove(m);
                    continue;
                }
                EnvironmentEnum ee = EnumUtil.getEnumByParameter(EnvironmentEnum.class, lastEe.getCode() + 1);
                if (ee == null) {
                    log.error("在获取部署环境配置枚举时出错");
                    ns.notifyByDingTalk(m, "在获取部署环境配置枚举时出错");
                    p.remove(m);
                    continue;
                }

                m.getJenkinsProcessBuildReqDTO().setTriggerByProgram(Boolean.TRUE);
                m.getJenkinsProcessBuildReqDTO().setBranch(getTag(ret));
                if (EnvironmentEnum.DEPLOY_SIT_UAT.getName().equals(ee.getName()) ||
                        EnvironmentEnum.DEPLOY_UAT.getName().equals(ee.getName())) {
                    m.getJenkinsProcessBuildReqDTO().setEnvironment(EnvironmentEnum.DEPLOY_UAT.getName());
                    jps.build(m.getJenkinsProcessBuildReqDTO());
                } else if (EnvironmentEnum.DEPLOY_PROD.getName().equals(ee.getName())) {
                    m.getJenkinsProcessBuildReqDTO().setEnvironment(EnvironmentEnum.DEPLOY_PROD.getName());
                    jps.build(m.getJenkinsProcessBuildReqDTO());
                } else {
                    log.info("");
                    ns.notifyByDingTalk(m, "创建Tag过程被人为取消");
                }
                p.remove(m);
            } else if (ret.contains("Finished: ABORTED")) {
                // 打 Tag 被主动取消
                log.error("创建Tag过程被人为取消，通知用户");
                p.remove(m);
                ns.notifyByDingTalk(m, "创建Tag过程被人为取消");
            }
        }
    }

    @Scheduled(cron = "00 01 17 * * ?")
    public void waitingTaskBuild() {
        if (monitorWaitingMap.isEmpty()) {
            return;
        }
        monitorWaitingMap.forEach((k,v)->{
            jps.build(v);
            monitorWaitingMap.remove(k);
        });
    }

    private void updateTaskStatusAsync(MonitorQueueItemEntity m, String status) {
        log.info("构建任务开始存入数据库");
        LambdaUpdateChainWrapper<JenkinsProcessHistory> updateChainWrapper = new LambdaUpdateChainWrapper<>(jphm);
        if (!updateChainWrapper.eq(JenkinsProcessHistory::getTaskId, m.getTaskId()).set(JenkinsProcessHistory::getResult, status).update()) {
            log.error("jenkins process history更新数据库失败");
            return;
        }
        log.info("构建任务成功存入数据库");
//        CompletableFuture.runAsync(() -> {
//
//        }, customTaskExecutor);
    }

    private String obtainCommitId(String consoleStr) {
        int start = consoleStr.indexOf("Commit_id:");
        consoleStr = consoleStr.substring(start);
        int end = consoleStr.indexOf("[");
        return consoleStr.substring(0, end).replace("Commit_id:", "").replace(" ", "")
                .replace("\n", "").replace("\r", "");
    }


    private String obtainSonarqube(String consoleStr) {
        int start = consoleStr.indexOf("https://sonarqube.maezia.com/dashboard/index");
        consoleStr = consoleStr.substring(start);
        int end = consoleStr.indexOf("[INFO]");
        return consoleStr.substring(0, end).replace("\n", "").replace("\r", "");
    }

    private String getTag(String ret) {
        int start = ret.indexOf("[INFO] TAG");
        ret = ret.substring(start);
        int end = ret.indexOf("创建成功");
        String tag = ret.substring(0, end).replace(" ","").replace("[INFO]TAG","")
                .replace("\\u001b","");
        log.info("tag={}", tag);
        return tag;
    }
}
