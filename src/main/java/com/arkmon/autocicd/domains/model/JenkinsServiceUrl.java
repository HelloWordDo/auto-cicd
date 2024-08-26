package com.arkmon.autocicd.domains.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("t_autocicd_jenkins_service_url")
public class JenkinsServiceUrl {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String project;
    private String environment;
    private String service;
    private String serviceFullName;
    private String serviceTagName;
    private String baseUrl;
}
