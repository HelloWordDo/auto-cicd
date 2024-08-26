package com.arkmon.autocicd.domains.dto.request;

import com.arkmon.autocicd.annotations.EnumCheck;
import com.arkmon.autocicd.enums.EnvironmentEnum;
import com.arkmon.autocicd.enums.ServiceNameEnum;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author X.J
 * @date 2021/2/7
 */
@Data
public class JenkinsProcessBuildReqDTO {
    /**
     * 项目名称
     */
    @NotBlank(message = "项目必填")
//    @EnumCheck(message = "projectName输入有误", enumClass = ProjectNameEnum.class)
    private String projectName;
    /**
     * 部署环境
     */
    @EnumCheck(message = "环境有误", enumClass = EnvironmentEnum.class)
    private String environment;
    /**
     * 申请人
     */
    @NotBlank(message = "申请人必填")
    private String applicant;

    /**
     * 申请部署原因
     */
    @NotBlank(message = "申请原因必填")
    private String reason;
    /**
     * jira上对应的jira ticket id
     */
    @NotBlank(message = "JIRA ID必填")
    private String jiraId;
    /**
     * 服务名
     */
    @EnumCheck(message = "服务名有误", enumClass = ServiceNameEnum.class)
    private String serviceName;

    /**
     * 部署分支
     */
    @NotBlank(message = "分支必填")
    private String branch;

    /**
     * 当周release版本号
     */
    @NotBlank(message = "发版号必填")
    private String releaseVersion;

    @Builder.Default
    private Boolean deployImmediate = true;

    /**
     * 是否由定时器触发，用于判断传入的环境是否需要从SIT环境开始部署,
     * 如果为false，则从SIT环境开始部署
     */
    @Builder.Default
    private Boolean triggerByProgram = Boolean.FALSE;

    private Boolean needSilence;
}
