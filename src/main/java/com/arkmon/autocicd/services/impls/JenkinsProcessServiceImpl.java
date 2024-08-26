package com.arkmon.autocicd.services.impls;

import Exceptions.MAException;
import ReturnEntities.ReturnData;
import com.arkmon.autocicd.configs.CustomsConfig;
import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;
import com.arkmon.autocicd.domains.model.JenkinsProcessHistory;
import com.arkmon.autocicd.domains.model.JenkinsServiceUrl;
import com.arkmon.autocicd.domains.model.UserInfo;
import com.arkmon.autocicd.enums.*;
import com.arkmon.autocicd.mappers.JenkinsProcessHistoryMapper;
import com.arkmon.autocicd.services.IntegrationTestService;
import com.arkmon.autocicd.services.JenkinsProcessService;
import com.arkmon.autocicd.services.NotificationService;
import com.arkmon.autocicd.utils.EnumUtil;
import com.arkmon.autocicd.utils.JenkinsUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * author X.J
 * date 2021/2/7
 */
@Service
@Slf4j
public class JenkinsProcessServiceImpl implements JenkinsProcessService {

    @Autowired
    private CustomsConfig cc;

    @Resource(name = "monitorBuildQueue")
    private Queue<MonitorQueueItemEntity> monitorQueue;

    @Resource(name = "obtainJenkinsServiceUrl")
    private Map<String, JenkinsServiceUrl> obtainJenkinsServiceUrl;

    @Resource
    private IntegrationTestService its;

    @Resource
    private NotificationService ns;

    @Autowired
    private ThreadPoolTaskExecutor customTaskExecutor;

    @Resource
    private JenkinsProcessHistoryMapper jphm;

    @Resource(name = "obtainUserInfo")
    private Map<String, UserInfo> userInfo;


    @Override
    public ReturnData build(JenkinsProcessBuildReqDTO dto) {
        // 判断当前时间是否满足17点以后，满足才可以部署UAT环境，不满足放到等待队列中，当天下午17点后统一部署
        EnvironmentEnum ee = EnumUtil.getEnumByParameter(EnvironmentEnum.class, dto.getEnvironment());
        String environment = Optional.ofNullable(ee).map(EnvironmentEnum::getName).orElseThrow(() -> new MAException("environment有误"));

        String taskId = UUID.randomUUID().toString().replace("-", "");
        initTaskStatusAsync(taskId, dto);
        String projectName = Optional.ofNullable(EnumUtil.getEnumByParameter(ProjectNameEnum.class, dto.getProjectName())).map(ProjectNameEnum::getName).orElseThrow(() -> new MAException("projectName有误"));

        String serviceName = Optional.ofNullable(EnumUtil.getEnumByParameter(ServiceNameEnum.class, dto.getServiceName())).map(ServiceNameEnum::getName).orElseThrow(() -> new MAException("serviceName有误"));
        String branch = dto.getBranch();
        String originalEnvironment = environment;
        // 如果不是定时器，即用户，调用该方法，所有SIT及以上环境（SIT，UAT，PROD）均需要从SIT环境部署开始，如果是定时器触发该方法，则environment保持不变
        if (!dto.getTriggerByProgram()) {
            environment = EnvironmentEnum.DEPLOY_DEV.getName().equals(environment) ? environment : EnvironmentEnum.DEPLOY_SIT.getName();
        }
        JenkinsServiceUrl jenkinsServiceUrl = obtainJenkinsServiceUrl.get(String.format("%s-%s-%s", projectName, environment, serviceName));
        if (jenkinsServiceUrl == null) {
            if (dto.getTriggerByProgram()) {
                log.error("部署环境{}暂不支持, 请联系管理员进行配置", environment);
                ns.notifyByDingTalk(MonitorQueueItemEntity.builder()
                        .jenkinsProcessBuildReqDTO(dto)
                        .ts(System.currentTimeMillis())
                        .build(), String.format("部署环境{}暂不支持, 请联系管理员进行配置", environment));
            }
            return new ReturnData("-1", "部署环境不支持, 请联系管理员进行配置");
        }
        String buildBaseUrl = String.format(jenkinsServiceUrl.getBaseUrl(), cc.getJenkins().getUser(), cc.getJenkins().getToken());
        String jenkinsLastBuildUrl = buildBaseUrl + "/lastBuild/buildNumber";
        String lastBuildNumber = Optional.ofNullable(JenkinsUtil.callJenkinsApi(jenkinsLastBuildUrl)).map(ret -> ret.replace("\n","").replace("\r","")).orElseThrow(() -> new MAException("获取 lastBuildNumber 失败"));
        Integer lastBuild;
        try {
            lastBuild = Integer.valueOf(lastBuildNumber);
        } catch (Exception e) {
            log.error("获取 lastBuildNumber 失败，jenkins 返回 lastBuildNumber={}", lastBuildNumber);
            return new ReturnData("-1", "获取 lastBuildNumber 失败");
        }

        String jenkinsBuildUrl = buildBaseUrl + "/buildWithParameters?branch=" + branch;
        JenkinsUtil.callJenkinsApi(jenkinsBuildUrl);
        dto.setEnvironment(environment);
        monitorQueue.add(MonitorQueueItemEntity.builder()
                .jenkinsProcessBuildReqDTO(dto)
                .ts(System.currentTimeMillis())
                .lastBuild(lastBuild)
                .originalEnvironment(originalEnvironment)
                .taskId(taskId)
                .build());
        log.info(monitorQueue.toString());
        updateTaskStatusAsync(taskId);
        log.info("{}环境部署触发成功", environment);
        return new ReturnData(ReturnParameterEnum.SUCCESS.getCode(), "立刻部署触发成功，部署结果会通过顶顶群通知");
    }

    private void updateTaskStatusAsync(String taskId) {
        log.info("开始更新jenkins process history");
        LambdaUpdateChainWrapper<JenkinsProcessHistory> updateChainWrapper = new LambdaUpdateChainWrapper<>(jphm);
        if (updateChainWrapper.eq(JenkinsProcessHistory::getTaskId, taskId)
                .set(JenkinsProcessHistory::getResult, JenkinsProcessEnum.PROCESSING.getName()).update()) {
            log.error("jenkins process history更新数据库失败");
            return;
        }
        log.info("成功更新jenkins process history");
//        CompletableFuture.runAsync(()->{
//
//        }, customTaskExecutor);
    }

    private void initTaskStatusAsync(String taskId, JenkinsProcessBuildReqDTO dto) {
        log.info("开始创建jenkins process history");
        UserInfo userInfo = this.userInfo.get(dto.getApplicant());
        String applicant = dto.getApplicant();
        if (userInfo != null) {
            applicant = userInfo.getUserName();
        }
        JenkinsProcessHistory jph = JenkinsProcessHistory.builder()
                .taskId(taskId)
                .applicant(applicant).branch(dto.getBranch()).environment(dto.getEnvironment())
                .project(dto.getProjectName()).releaseVersion(dto.getReleaseVersion())
                .result(JenkinsProcessEnum.READY.getName()).service(dto.getServiceName())
                .build();
        if (jphm.insert(jph) != 1) {
            log.error("jenkins process history插入数据库失败");
            return;
        }
        log.info("成功创建jenkins process history");
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
//
//        }, customTaskExecutor);
    }
}
