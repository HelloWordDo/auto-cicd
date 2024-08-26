package com.arkmon.autocicd.domains.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_autocicd_release_note")
@Builder
public class ReleaseNote {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userId;
    private String project;
    private String service;
    private String environment;
    private String reason;
    private String tag;
    private String commitId;
    private String jiraId;
    private Date creationDate;
}
