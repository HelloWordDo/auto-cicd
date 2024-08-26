package com.arkmon.autocicd.services;

import Exceptions.MAException;
import ReturnEntities.ReturnData;
import com.arkmon.autocicd.domains.model.JenkinsServiceUrl;
import com.arkmon.autocicd.domains.model.JenkinsTagUrl;
import com.arkmon.autocicd.domains.model.UserInfo;
import com.arkmon.autocicd.enums.ReturnParameterEnum;
import com.arkmon.autocicd.events.UserRegisterEvent;
import com.arkmon.autocicd.mappers.JenkinsServiceUrlMapper;
import com.arkmon.autocicd.mappers.JenkinsTagUrlMapper;
import com.arkmon.autocicd.mappers.UserInfoMapper;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author X.J
 * @date 2021/2/4
 */

@Service
@Slf4j
@Scope("prototype")
public class UserService implements ApplicationEventPublisherAware {
    @Resource
    private UserInfoMapper uim;
    @Resource
    private JenkinsServiceUrlMapper jsum;
    @Resource
    private JenkinsTagUrlMapper jtum;

    @Resource(name = "obtainUserInfo")
    private Map<String, UserInfo> userInfo;
    @Resource(name = "obtainJenkinsServiceUrl")
    private Map<String, JenkinsServiceUrl> jsu;
    @Resource(name = "obtainJenkinsTagUrl")
    private Map<String, JenkinsTagUrl> jtu;

    // 注入事件发布者
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 发布事件
     */
    public void register(String username) {
        log.info("执行用户[{}]的注册逻辑", username);
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, username));
    }

    public ReturnData refresh() {
        List<UserInfo> userInfos = uim.selectList(null);
        Optional.ofNullable(userInfos).orElseThrow(() -> new MAException("用户信息表为空，请修正"));
        Map<String, UserInfo> collect = userInfos.stream().collect(Collectors.toMap(UserInfo::getUserName, Function.identity()));
        List<JenkinsServiceUrl> jenkinsServiceUrls = jsum.selectList(null);
        Optional.ofNullable(jenkinsServiceUrls).orElseThrow(() -> new MAException("用户信息表为空，请修正"));
        Map<String, JenkinsServiceUrl> collect2 = jenkinsServiceUrls.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s-%s", k.getProject(), k.getEnvironment(), k.getService()),
                Function.identity()));
        List<JenkinsTagUrl> jenkinsTagUrls = jtum.selectList(null);
        Optional.ofNullable(jenkinsTagUrls).orElseThrow(() -> new MAException("用户信息表为空，请修正"));
        Map<String, JenkinsTagUrl> collect3 = jenkinsTagUrls.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s", k.getProject(), k.getEnvironment()),
                Function.identity()));
        synchronized (this){
            userInfo.clear();
            userInfo.putAll(collect);
            jsu.clear();
            jsu.putAll(collect2);
            jtu.clear();
            jtu.putAll(collect3);
        }
        return new ReturnData(ReturnParameterEnum.SUCCESS.getCode(), ReturnParameterEnum.SUCCESS.getName());
    }
}
