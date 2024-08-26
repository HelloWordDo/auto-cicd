package com.arkmon.autocicd.aspects;

import Exceptions.MAException;
import ReturnEntities.ReturnData;
import com.arkmon.autocicd.domains.dto.request.JenkinsProcessBuildReqDTO;
import com.arkmon.autocicd.domains.model.DeployDeniedWindow;
import com.arkmon.autocicd.domains.model.UserAccess;
import com.arkmon.autocicd.domains.model.UserInfo;
import com.arkmon.autocicd.enums.EnvironmentEnum;
import com.arkmon.autocicd.enums.ReturnParameterEnum;
import com.arkmon.autocicd.enums.UserRoleEnum;
import com.arkmon.autocicd.utils.EnumUtil;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

/**
 * 用户权限校验
 *
 * @author X.J
 * @date 2021/3/1
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class UserAccessCheckAspect {
    @Resource(name = "obtainUserInfo")
    private Map<String, UserInfo> userInfoMap;
    @Resource(name = "obtainUserAccess")
    private Map<String, UserAccess> userAccessMap;

    @Resource(name = "obtainDeniedWindow")
    Map<String, DeployDeniedWindow> obtainDeniedWindow;

    @Resource(name = "monitorWaitingMap")
    private Map<String, JenkinsProcessBuildReqDTO> monitorWaitingMap;

    private final LocalTime UAT_ALLOWED_DEPLOPY_START = LocalTime.of(17, 00, 0);
    private final LocalTime UAT_ALLOWED_DEPLOPY_END = LocalTime.of(23, 59, 59);


    @Pointcut("@annotation(com.arkmon.autocicd.annotations.AccessCheck)")
    public void accessCheck() {

    }

    @Before(value = "accessCheck()")
    public void before(JoinPoint joinPoint) {
        JenkinsProcessBuildReqDTO jenkinsProcessBuildReqDTO = (JenkinsProcessBuildReqDTO) joinPoint.getArgs()[0];
        log.info("开始部署前置条件判断");
        log.info("判断当日是否可以部署");
        checkTodayDeploy(jenkinsProcessBuildReqDTO);
        log.info("当日可以部署");
        log.info("开始验证用户{}权限", jenkinsProcessBuildReqDTO.getApplicant());
        checkUserAccess(jenkinsProcessBuildReqDTO);
        log.info("用户权限验证通过");
        log.info("判断是否需要延迟部署");
        checkDelayDeploy(jenkinsProcessBuildReqDTO);
    }

    private void checkDelayDeploy(JenkinsProcessBuildReqDTO dto) {
        UserInfo userInfo = userInfoMap.get(dto.getApplicant());
        if (UserRoleEnum.ADMIN.getName().equalsIgnoreCase(userInfo.getUserRole())) {
            log.info("用户{}为{}权限，可立刻触发部署", dto.getApplicant(), userInfo.getUserRole());
            return;
        }
        log.info("用户{}为{}权限，当日17点后触发部署", dto.getApplicant(), userInfo.getUserRole());
        EnvironmentEnum ee = EnumUtil.getEnumByParameter(EnvironmentEnum.class, dto.getEnvironment());
        if (ee.getCode() >= 2 && !isAtUatDeployWindow()) {
            // 当部署环境是UAT及其以上 且 不在UAT部署的窗口期（17：00-23：59：59），进入此分支
            log.info("UAT部署请求，进入等待队列，17点起开始部署");
            String key = String.format("%s-%s-%s", dto.getProjectName(), dto.getServiceName(), dto.getEnvironment());
            JenkinsProcessBuildReqDTO originalOne = monitorWaitingMap.get(key);
            if (originalOne == null) {
                monitorWaitingMap.put(key, dto);
                throw new MAException("已加入部署等待队列，将于当天下午17点起开始部署，届时请查收钉钉群通知", ReturnParameterEnum.SUCCESS.getCode());
            }
            dto.setJiraId(originalOne.getJiraId().concat(";").concat(dto.getJiraId()));
            dto.setApplicant(originalOne.getApplicant().concat(";").concat(dto.getApplicant()));
            dto.setReason(originalOne.getReason().concat(";").concat(dto.getReason()));
            monitorWaitingMap.put(key, dto);
            throw new MAException("已加入部署等待队列，将于当天下午17点起开始部署，届时请查收钉钉群通知", ReturnParameterEnum.SUCCESS.getCode());
        }
    }


    private void checkTodayDeploy(@NotNull JenkinsProcessBuildReqDTO dto) {
        String projectName = dto.getProjectName();
        String env = dto.getEnvironment();
        DeployDeniedWindow deployDeniedWindow = obtainDeniedWindow.get(String.format("%s-%s", projectName, env));
        if (deployDeniedWindow == null) {
            throw new MAException(String.format("%s-%s 未受到denied window表管控，部署终止", projectName, env));
        }
        long crtUnixTime = System.currentTimeMillis();
        if (deployDeniedWindow.getDeniedStart() != null && deployDeniedWindow.getDeniedEnd() != null) {
            long deniedStartUnixTime = deployDeniedWindow.getDeniedStart().getTime();
            long deniedEndUnixTime = deployDeniedWindow.getDeniedEnd().getTime();
            if (deniedStartUnixTime <= crtUnixTime && crtUnixTime <= deniedEndUnixTime) {
                throw new MAException(String.format("%s-%s 正处于部署管制阶段，请在%s后尝试部署", projectName, env, deployDeniedWindow.getDeniedEnd().toString()));
            }
        } else if (deployDeniedWindow.getDeniedStart() == null && deployDeniedWindow.getDeniedEnd() != null) {
            if (crtUnixTime <= deployDeniedWindow.getDeniedEnd().getTime()) {
                throw new MAException(String.format("%s-%s 正处于部署管制阶段，请在%s后尝试部署", projectName, env, deployDeniedWindow.getDeniedEnd().toString()));
            }
        } else if (deployDeniedWindow.getDeniedStart() != null && deployDeniedWindow.getDeniedEnd() == null) {
            throw new MAException(String.format("%s-%s 正处于部署管制阶段，恢复部署时间待定", projectName, env));
        }
        UserInfo userInfo = userInfoMap.get(dto.getApplicant());
        if (UserRoleEnum.ADMIN.getName().equalsIgnoreCase(userInfo.getUserRole())) {
            return;
        }
    }

    private void checkUserAccess(@NotNull JenkinsProcessBuildReqDTO reqDTO) {
        UserInfo userInfo = userInfoMap.get(reqDTO.getApplicant());
        if (userInfo== null || UserRoleEnum.GUEST.getName().equals(userInfo.getUserRole())) {
            throw new MAException(String.format("用户:%s鉴权失败", reqDTO.getApplicant()));
        }
        if (UserRoleEnum.ADMIN.getName().equalsIgnoreCase(userInfo.getUserRole())) {
            return;
        }
        if (!UserRoleEnum.OWNER.getName().equalsIgnoreCase(userInfo.getUserRole())) {
            throw new MAException(String.format("用户:%s鉴权失败", reqDTO.getApplicant()));
        }
        String key = String.format("%s-%s-%s-%s", userInfo.getUserId(), reqDTO.getProjectName(),
                reqDTO.getServiceName(), reqDTO.getEnvironment());
        UserAccess userAccess = userAccessMap.get(key);
        if (userAccess == null || userAccess.getServiceDeployAccess() != 1) {
            throw new MAException(String.format("用户:%s没有权限部署%s-%s-%s", reqDTO.getApplicant(),
                    reqDTO.getProjectName(), reqDTO.getServiceName(), reqDTO.getEnvironment()));
        }
    }

    private boolean isAtUatDeployWindow() {
        LocalTime now = LocalTime.now();
        return now.isAfter(UAT_ALLOWED_DEPLOPY_START) && now.isBefore(UAT_ALLOWED_DEPLOPY_END);
    }
}
