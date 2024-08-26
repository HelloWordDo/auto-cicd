package com.arkmon.autocicd.domains.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_autocicd_jenkins_tag_url")
public class JenkinsTagUrl {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String project;
    private String environment;
    private String baseUrl;
}
