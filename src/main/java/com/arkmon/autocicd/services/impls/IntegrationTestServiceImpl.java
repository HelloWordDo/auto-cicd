package com.arkmon.autocicd.services.impls;

import Exceptions.MAException;
import com.arkmon.autocicd.configs.CustomsConfig;
import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import com.arkmon.autocicd.domains.dto.response.YapiAutoTestRspDTO;
import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;
import com.arkmon.autocicd.domains.model.JenkinsServiceUrl;
import com.arkmon.autocicd.domains.model.JenkinsTagUrl;
import com.arkmon.autocicd.domains.model.YapiSetting;
import com.arkmon.autocicd.enums.EnvironmentEnum;
import com.arkmon.autocicd.enums.ProjectNameEnum;
import com.arkmon.autocicd.enums.ServiceNameEnum;
import com.arkmon.autocicd.events.JenkinsProcessEvent;
import com.arkmon.autocicd.mappers.YapiSettingMapper;
import com.arkmon.autocicd.services.IntegrationTestService;
import com.arkmon.autocicd.services.NotificationService;
import com.arkmon.autocicd.services.ReleaseNoteManagementService;
import com.arkmon.autocicd.utils.EnumUtil;
import com.arkmon.autocicd.utils.FileUtil;
import com.arkmon.autocicd.utils.JenkinsUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;
import utils.JsonUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * 集成测试模块服务实现类
 *
 * @author X.J
 * @date 2021/2/25
 */
@Service
@Slf4j
public class IntegrationTestServiceImpl implements IntegrationTestService, ApplicationEventPublisherAware, ApplicationListener<JenkinsProcessEvent> {

    @Resource
    private RestTemplate rt;
    @Resource
    private CustomsConfig cc;
    @Resource
    private YapiSettingMapper ysm;
    @Resource
    private NotificationService ns;
    @Resource(name = "monitorTagQueue")
    private Queue<MonitorQueueItemEntity> mtq;
    @Resource
    private ReleaseNoteManagementService rms;
    @Resource
    private Map<String, JenkinsTagUrl> obtainJenkinsTagUrl;
    @Resource
    private Map<String, JenkinsServiceUrl> obtainJenkinsServiceUrl;


    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Set the ApplicationEventPublisher that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param applicationEventPublisher event publisher to be used by this object
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void start(MonitorQueueItemEntity m) {
        log.info("请求集成测试");
        applicationEventPublisher.publishEvent(new JenkinsProcessEvent(this, m));
    }

    /**
     * 监听集成测试启动信号，收到信号，启动集成测试
     *
     * @param event the event to respond to
     */
    @Override
    @Async("customTaskExecutor")
    public void onApplicationEvent(JenkinsProcessEvent event) {
        log.info("开始进行{}集成测试，{}", event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getEnvironment(), event.getMonitorQueueItem());
        // 集成测试逻辑实现：：需要注意的是，当jenkins触发部署后，k8s需要一定的时间来启动服务，此时调用yapi的集成测试接口，会报错。
        JenkinsProcessBuildReqDTO jpb = event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO();
        YapiSetting ys = ysm.selectOne(new QueryWrapper<>(YapiSetting.builder()
                .serviceName(jpb.getServiceName())
                .projectName(jpb.getProjectName())
                .projectEnv(jpb.getEnvironment())
                .build()));
        if (ys == null) {
            log.error("从数据库获取YAPI配置信息失败");
            ns.notifyByDingTalk(event.getMonitorQueueItem(), "集成测试失败，从数据库获取YAPI配置信息为空");
            return;
        }

        String autoTestRunUrl = String.format(cc.getYapi().getUrl(), ys.getYapiEnvId(), ys.getYapiProjectToken(),
                ys.getYapiProjectId(), ys.getYapiEnv());
//        //服务刚部署，在k8s中需要一段时间重新启动，所以这里等待2min，再进行测试。先这么写，后面看是否有更好的方式
//        log.info("进入休眠状态，2min后唤醒...");
//        try {
//            Thread.sleep(1000 * 60 * 2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ResponseEntity<String> entity = rt.getForEntity(autoTestRunUrl, String.class);
        if (!entity.getStatusCode().is2xxSuccessful()) {
            log.error("请求自动化集成测试接口失败，http status：{}", entity.getStatusCodeValue());
            ns.notifyByDingTalk(event.getMonitorQueueItem(), "集成测试失败，请求自动化集成测试接口失败，http status：" + entity.getStatusCodeValue());
            return;
        }
        //集成测试报告存入minio
        saveTestReport(jpb,event, entity.getBody());

        YapiAutoTestRspDTO rspDTO;
        try {
            rspDTO = JsonUtil.jsonStr2Obj(entity.getBody(), YapiAutoTestRspDTO.class);
        } catch (Exception e) {
            log.error("获取{}集成测试结果失败",event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getEnvironment());
            ns.notifyByDingTalk(event.getMonitorQueueItem(),
                    String.format("获取%s集成测试结果失败", event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getEnvironment()));
            return;
        }
        if (rspDTO == null) {
            log.error("从数据库获取YAPI配置信息失败");
            ns.notifyByDingTalk(event.getMonitorQueueItem(), "集成测试失败，从数据库获取YAPI配置信息为空");
            return;
        }
        if (rspDTO.getMessage().getFailedNum() != 0) {
            // 集成测试失败
            log.info("{}集成测试用例有{}个未通过，通知用户，CICD终止",
                    event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getEnvironment(),
                    rspDTO.getMessage().getFailedNum());
            List<YapiAutoTestRspDTO.AutoTestDetail> failedTestCases = rspDTO.getList().stream().filter(item -> item.getCode() == 1).collect(Collectors.toList());
            StringBuilder failedTestCasesStr = new StringBuilder();
            for (int i = 0; i < failedTestCases.size(); i++) {
                failedTestCasesStr.append("-").append(i).append(". id: ".concat(String.valueOf(failedTestCases.get(i).getId()))).append("\n");
                failedTestCasesStr.append("     name: ".concat(String.valueOf(failedTestCases.get(i).getName()))).append("\n");
                failedTestCasesStr.append("     path: ".concat(String.valueOf(failedTestCases.get(i).getPath()))).append("\n");
            }
            ns.notifyByDingTalk(event.getMonitorQueueItem(),
                    String.format("%s集成测试用例有%s个未通过，通知用户，CICD终止\n失败用例：\n%s",
                            event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getEnvironment(),
                            rspDTO.getMessage().getFailedNum(),
                            failedTestCasesStr
                    ));
        } else {
            // 集成测试成功
            String crtEnvironment = event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getEnvironment();
            log.info("{}集成测试通过，保存release note...", crtEnvironment);
            rms.save(event.getMonitorQueueItem());
            log.info("判断是否需要打TAG...");
            String originalEnvironment = event.getMonitorQueueItem().getOriginalEnvironment();
            EnvironmentEnum originalEnvironmentEnum = EnumUtil.getEnumByParameter(EnvironmentEnum.class, originalEnvironment);
            if (originalEnvironmentEnum == null) {
                log.error("无法判断后续部署环境，CICD终止，通知用户");
                ns.notifyByDingTalk(event.getMonitorQueueItem(), "无法判断后续部署环境，通知用户");
                return;
            }
            String project = event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getProjectName();
            // 判断是否需要打TAG: 当原始部署环境不是UAT/PROD时，无需打Tag
            if (!EnvironmentEnum.DEPLOY_UAT.getName().equals(originalEnvironmentEnum.getName()) &&
                    !EnvironmentEnum.DEPLOY_PROD.getName().equals(originalEnvironmentEnum.getName())) {
                log.info("{}环境部署成功，集成测试成功。无需打TAG & 后续部署，CICD终止，通知用户",
                        event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getEnvironment());
                ns.notifyByDingTalk(event.getMonitorQueueItem(), originalEnvironment + "环境部署成功，集成测试成功");
                return;
            }
            // 判断是否需要打TAG: 当原始部署环境 等于 当前已成功部署且集成测试通过的环境，无需打Tag
            if (crtEnvironment == originalEnvironment) {
                log.info("{}环境部署成功，集成测试成功。无需打TAG & 后续部署，CICD终止，通知用户",
                        event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getEnvironment());
                ns.notifyByDingTalk(event.getMonitorQueueItem(), originalEnvironment + "环境部署成功，集成测试成功");
                return;
            }

            EnvironmentEnum crtEnvironmentEnum = EnumUtil.getEnumByParameter(EnvironmentEnum.class, crtEnvironment);
            EnvironmentEnum nextEnvironmentEnum = EnumUtil.getEnumByParameter(EnvironmentEnum.class, crtEnvironmentEnum.getCode() + 1);
            JenkinsTagUrl jtu = obtainJenkinsTagUrl.get(String.format("%s-%s", project, nextEnvironmentEnum.getName()));
            if (jtu == null) {
                log.error("未找到{}-{}下的Tag Url，通知用户", project, originalEnvironment);
                ns.notifyByDingTalk(event.getMonitorQueueItem(), crtEnvironment + "环境部署成功，集成测试成功。但打标签失败。CICD终止");
                return;
            }
            String tagBaseUrl = String.format(jtu.getBaseUrl(), cc.getJenkins().getUser(), cc.getJenkins().getToken());
            log.info("开始打TAG...");
            String jenkinsLastBuildUrl = tagBaseUrl + "/lastBuild/buildNumber";
            String lastBuildNumber = Optional.ofNullable(JenkinsUtil.callJenkinsApi(jenkinsLastBuildUrl))
                    .map(ret -> ret.replace("\n","").replace("\r",""))
                    .orElseThrow(() -> new MAException("获取 lastBuildNumber 失败"));
            Integer lastBuild;
            try {
                lastBuild = Integer.valueOf(lastBuildNumber);
            } catch (Exception e) {
                log.error("获取 lastBuildNumber 失败，jenkins 返回 lastBuildNumber={}", lastBuildNumber);
                ns.notifyByDingTalk(event.getMonitorQueueItem(),"打Tag失败");
                return;
            }
            ProjectNameEnum pne = EnumUtil.getEnumByParameter(ProjectNameEnum.class, jpb.getProjectName());
            String tagProjectVersion = String.format("%s_%s",
                    pne.getJenkinsProjectName(),
                    jpb.getReleaseVersion()
            );
            String tmp;
            if (EnvironmentEnum.DEPLOY_UAT.getName().equals(originalEnvironment)) {
                tmp = EnvironmentEnum.DEPLOY_SIT.getName();
            } else if (EnvironmentEnum.DEPLOY_PROD.getName().equals(originalEnvironment)) {
                tmp = EnvironmentEnum.DEPLOY_UAT.getName();
            } else {
                log.error("{}环境无需打标签", originalEnvironment);
                ns.notifyByDingTalk(event.getMonitorQueueItem(), String.format("{}环境无需打标签", originalEnvironment));
                return;
            }
            JenkinsServiceUrl jenkinsServiceUrl = obtainJenkinsServiceUrl
                    .get(String.format("%s-%s-%s", project, originalEnvironment, jpb.getServiceName()));
            if (jenkinsServiceUrl == null) {
                log.error(String.format("未找到%s-%s-%s的服务全称", project, originalEnvironment, jpb.getServiceName()));
                ns.notifyByDingTalk(event.getMonitorQueueItem(),
                        String.format("未找到%s-%s-%s的服务全称", project, originalEnvironment, jpb.getServiceName()));
                return;
            }
            String jenkinsTagBuildUrl = tagBaseUrl +
                    String.format("/buildWithParameters?cloud=%s&namespace=%s&project_version=%s&service=%s",
                            "ma", jpb.getProjectName().concat("-").concat(tmp), tagProjectVersion,
                            jenkinsServiceUrl.getServiceTagName());
            log.info("tag build url:{}", jenkinsTagBuildUrl);
            JenkinsUtil.callJenkinsApi(jenkinsTagBuildUrl);
            event.getMonitorQueueItem().setTs(System.currentTimeMillis());
            event.getMonitorQueueItem().setLastBuild(lastBuild);
            event.getMonitorQueueItem().setTagBaseUrl(tagBaseUrl);
            mtq.add(event.getMonitorQueueItem());
        }

    }

    private void saveTestReport(JenkinsProcessBuildReqDTO jpb, JenkinsProcessEvent event, String body) {
        /**
         * 将集成测试报告存入minio
         * @author xj
         * @date 2:51 下午 2021/3/16
         * @param [jpb, event, body]
         * @return void
         */
        MinioClient minioClient = MinioClient.builder()
                .endpoint(cc.getMinio().getUrl())
                .credentials(cc.getMinio().getAccessKey(), cc.getMinio().getSecretKey())
                .build();
        String fileNamePrefix = String.format("%s_%s_%s_%s_%s", jpb.getProjectName(), jpb.getServiceName(), jpb.getEnvironment(),jpb.getApplicant(), System.currentTimeMillis());
        String fileName = fileNamePrefix.concat(".json");
        String zipName = fileNamePrefix.concat(".zip");
        try {
            boolean b = FileUtil.string2File(body, fileName);
            boolean b1 = FileUtil.string2ZipFile(fileName, zipName);
            if (b&&b1) {
                boolean found =
                        minioClient.bucketExists(BucketExistsArgs.builder().bucket(cc.getMinio().getBucket()).build());
                if (!found) {
                    // Make a new bucket.
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(cc.getMinio().getBucket()).build());
                }
                minioClient.uploadObject(UploadObjectArgs.builder().bucket(cc.getMinio().getBucket()).object(zipName)
                        .filename(zipName)
                        .build()
                );
                event.getMonitorQueueItem().setIntegratedTestFile(String.format("%s at %s", zipName,
                        cc.getMinio().getUrl().concat("/minio/autocicd")));
                log.info("存储集成测试报告成功");
            } else {
                log.error("存储集成测试报告失败");
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            log.error("存储集成测试报告失败");
            throw new MAException(e.toString());
        } finally {
            try {
                if (!FileSystemUtils.deleteRecursively(Paths.get(fileName))) {
                    log.error("文件{}删除失败", fileName);
                }
                if (!FileSystemUtils.deleteRecursively(Paths.get(zipName))) {
                    log.error("文件{}删除失败", zipName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

