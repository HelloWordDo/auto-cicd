package com.arkmon.autocicd.configs;

import Exceptions.MAException;
import com.arkmon.autocicd.domains.model.*;
import com.arkmon.autocicd.mappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户信息表
 *
 * @author X.J
 * @date 2021/3/1
 */
@Configuration
public class DataInitConfig {
    @Resource
    private UserInfoMapper uim;
    @Resource
    private UserAccessMapper uam;
    @Resource
    private JenkinsServiceUrlMapper jsum;
    @Resource
    private JenkinsTagUrlMapper jtum;
    @Resource
    private DeployDeniedWindowMapper ddwm;
    @Resource
    private ReleaseVersionMapper rvm;

    @Bean
    public Map<String, UserInfo> obtainUserInfo() {
        List<UserInfo> userInfos = uim.selectList(null);
        Optional.ofNullable(userInfos).orElseThrow(() -> new MAException("用户信息表为空，请修正"));
        return userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, Function.identity()));
    }

    @Bean
    public Map<String, UserAccess> obtainUserAccess() {
        List<UserAccess> userAccesses = uam.selectList(null);
        Optional.ofNullable(userAccesses).orElseThrow(() -> new MAException("用户权限表为空，请修正"));
        return userAccesses.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s-%s-%s", k.getUserId(), k.getProject(), k.getServiceName(), k.getEnvironment()),
                Function.identity()));
    }


    @Bean
    public Map<String, JenkinsServiceUrl> obtainJenkinsServiceUrl() {
        List<JenkinsServiceUrl> jenkinsServiceUrls = jsum.selectList(null);
        Optional.ofNullable(jenkinsServiceUrls).orElseThrow(() -> new MAException("Jenkins服务URL表为空，请修正"));
        return jenkinsServiceUrls.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s-%s", k.getProject(), k.getEnvironment(), k.getService()),
                Function.identity()));
    }

    @Bean
    public Map<String, JenkinsTagUrl> obtainJenkinsTagUrl() {
        List<JenkinsTagUrl> jenkinsTagUrls = jtum.selectList(null);
        Optional.ofNullable(jenkinsTagUrls).orElseThrow(() -> new MAException("Jenkins Tag URL表为空，请修正"));
        return jenkinsTagUrls.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s", k.getProject(), k.getEnvironment()),
                Function.identity()));
    }

    @Bean
    public Map<String, DeployDeniedWindow> obtainDeniedWindow() {
        List<DeployDeniedWindow> deployDeniedWindows = ddwm.selectList(null);
        Optional.ofNullable(deployDeniedWindows).orElseThrow(() -> new MAException("deploy denied window表为空，请修正"));
        return deployDeniedWindows.stream().collect(Collectors.toMap(
                k -> String.format("%s-%s", k.getProjectName(), k.getEnvironment()),
                Function.identity()));
    }

    @Bean
    public ReleaseVersion obtainReleaseVersion() {
        List<ReleaseVersion> releaseVersions = rvm.selectList(null);
        if (releaseVersions == null || releaseVersions.size() != 1) {
            throw new MAException("Release Version表有问题，请修正");
        }
        return releaseVersions.get(0);
    }

}
