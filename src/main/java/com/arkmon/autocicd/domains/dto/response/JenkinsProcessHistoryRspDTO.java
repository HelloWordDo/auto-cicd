package com.arkmon.autocicd.domains.dto.response;

import lombok.Data;

import java.util.Date;

/**
 * author     : X.J
 * date       : 2021/3/13-8:29 下午
 * description:
 */
@Data
public class JenkinsProcessHistoryRspDTO {
    private Long id;
    private String taskId;
    private String project;
    private String environment;
    private String service;
    private String branch;
    private String releaseVersion;
    private String applicant;
    private String result;
    private String creationDate;
}
