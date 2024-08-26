package com.arkmon.autocicd.domains.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息表
 * @author X.J
 * @date 2021/3/1
 */
@Data
@TableName("t_autocicd_useraccess")
public class UserAccess {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userId;
    private String project;
    private String serviceName;
    private String environment;
    private Integer serviceDeployAccess;
    private Date creationDate;
    private Date lastUpdateDate;
}
