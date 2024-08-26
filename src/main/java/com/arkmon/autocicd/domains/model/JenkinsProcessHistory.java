package com.arkmon.autocicd.domains.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@TableName("t_autocicd_jenkins_process_history")
public class JenkinsProcessHistory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskId;
    private String project;
    private String environment;
    private String service;
    private String branch;
    private String releaseVersion;
    private String applicant;
    private String result;
    private Date creationDate;
    private Date lastUpdateDate;
}
