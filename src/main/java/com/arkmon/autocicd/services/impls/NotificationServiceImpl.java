package com.arkmon.autocicd.services.impls;

import com.arkmon.autocicd.configs.CustomsConfig;
import com.arkmon.autocicd.configs.DingTalkConfig;
import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import com.arkmon.autocicd.domains.dto.response.DintTalkNotifyRspDTO;
import com.arkmon.autocicd.domains.entity.MonitorQueueItemEntity;
import com.arkmon.autocicd.domains.entity.TextMessage;
import com.arkmon.autocicd.domains.model.JenkinsServiceUrl;
import com.arkmon.autocicd.domains.model.UserInfo;
import com.arkmon.autocicd.enums.NotificationEnum;
import com.arkmon.autocicd.enums.UserRoleEnum;
import com.arkmon.autocicd.events.NotificationEvent;
import com.arkmon.autocicd.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 集成测试模块服务实现类
 *
 * @author X.J
 * @date 2021/2/25
 */
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService, ApplicationEventPublisherAware, ApplicationListener<NotificationEvent> {

    @Resource
    DingTalkConfig dtc;

    @Resource
    CustomsConfig cc;

    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private Map<String, UserInfo> obtainUserInfo;

    @Resource
    private Map<String, JenkinsServiceUrl> obtainJenkinsServiceUrl;

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

    /**
     * 监听集成测试启动信号，收到信号，启动集成测试
     *
     * @param event the event to respond to
     */
    @Override
    @Async("customTaskExecutor")
    public void onApplicationEvent(NotificationEvent event) {
        log.info("开始发送{}通知：{}-{}", event.getNotifyWay(),event.getMonitorQueueItem(), event.getMsg());
        //todo 具体通知逻辑实现
        switch (event.getNotifyWay()) {
            case "email":
                log.info("发送邮件通知");
                //todo 邮件通知实现
                log.info("完成邮件通知");
                break;
            case "dingTalk":
                log.info("开始钉钉通知");
                List<String> atMobiles = new ArrayList<>();
                obtainUserInfo.forEach((k,v)->{
                    if (UserRoleEnum.ADMIN.getName().equalsIgnoreCase(v.getUserRole())) {
                        atMobiles.add(v.getUserPhone());
                    }
                });
                if (event.getMonitorQueueItem() != null) {
                    UserInfo userInfo = obtainUserInfo.get(event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getApplicant());
                    if (userInfo != null && !StringUtils.isEmpty(userInfo.getUserPhone())) {
                        atMobiles.add(userInfo.getUserPhone());
                    }
                }
                log.info("发送通知给用户{}", atMobiles.toString());
                DintTalkNotifyRspDTO rspDTO = doDingTalkNotify(event, atMobiles, cc.getDingTalk().getAtAll());
                log.info("完成钉钉通知, 是否发送成功={}, 原因：{}", rspDTO.isSuccess(), rspDTO.getErrorMsg());
                break;
            default:
                log.error("");
                break;
        }
    }

    private DintTalkNotifyRspDTO doDingTalkNotify(NotificationEvent event, List<String> atMobiles, String atAll) {
        Boolean needSilence = event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO().getNeedSilence();
        if (needSilence != null && needSilence) {
            DintTalkNotifyRspDTO rspDTO = new DintTalkNotifyRspDTO();
            rspDTO.setIsSuccess(false);
            rspDTO.setErrorMsg("静默部署，无需通知");
            return rspDTO;
        }
        TextMessage textMessage;
        if (event.getMonitorQueueItem() == null) {
            textMessage = new TextMessage(event.getMsg());
        } else {
            JenkinsProcessBuildReqDTO jenkinsProcessBuildReqDTO = event.getMonitorQueueItem().getJenkinsProcessBuildReqDTO();
            UserInfo userInfo = obtainUserInfo.get(jenkinsProcessBuildReqDTO.getApplicant());
            JenkinsServiceUrl jenkinsServiceUrl = obtainJenkinsServiceUrl.get(String.format("%s-%s-%s", jenkinsProcessBuildReqDTO.getProjectName(),
                    jenkinsProcessBuildReqDTO.getEnvironment(), jenkinsProcessBuildReqDTO.getServiceName()));
            textMessage = new TextMessage("auto_cicd -->\n"
                    .concat("项目: ").concat(jenkinsProcessBuildReqDTO.getProjectName())
                    .concat("\n服务: ").concat(jenkinsProcessBuildReqDTO.getServiceName())
                    .concat("\n环境: ").concat(jenkinsProcessBuildReqDTO.getEnvironment())
                    .concat("\n申请人: ").concat(userInfo == null ? jenkinsProcessBuildReqDTO.getApplicant() : userInfo.getUserName())
                    .concat("\nJiraID: ").concat(jenkinsProcessBuildReqDTO.getJiraId())
                    .concat("\n申请原因: ").concat(jenkinsProcessBuildReqDTO.getReason())
                    .concat("\nJenkins地址: ").concat(jenkinsServiceUrl == null ? "" : jenkinsServiceUrl.getBaseUrl()
                            .replace("%s:%s@", ""))
                    .concat("\nSonarQube地址: ").concat(event.getMonitorQueueItem().getSonarqubeUrl() == null ?
                            "" : event.getMonitorQueueItem().getSonarqubeUrl())
                    .concat("\n集成测试报告: ".concat(event.getMonitorQueueItem().getIntegratedTestFile() == null ?
                            "" : event.getMonitorQueueItem().getIntegratedTestFile()))
                    .concat("\n消息: ").concat(event.getMsg())
            );
        }
        if ("true".equals(atAll)) {
            textMessage.setAtAll(true);
        } else {
            textMessage.setAtMobiles(atMobiles);
        }
        try {
            return dtc.send(cc.getDingTalk().getWebHookToken(), textMessage);
        } catch (IOException e) {
            log.error("发送钉钉消息出错");
            DintTalkNotifyRspDTO rspDTO = new DintTalkNotifyRspDTO();
            rspDTO.setIsSuccess(false);
            return rspDTO;
        }
    }

    @Override
    public void notifyByEmail(MonitorQueueItemEntity m, String msg) {
        log.info("请求发送邮件通知");
        applicationEventPublisher.publishEvent(new NotificationEvent(this, m, NotificationEnum.EMAIL.getName(), msg));
    }

    @Override
    public void notifyByDingTalk(MonitorQueueItemEntity m, String msg) {
        log.info("请求发送短信通知");
        applicationEventPublisher.publishEvent(new NotificationEvent(this, m, NotificationEnum.DING_TALK.getName(), msg));
    }
}

