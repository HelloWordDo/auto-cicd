package com.arkmon.autocicd.domains.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息表
 * @author X.J
 * @date 2021/3/1
 */
@Data
@TableName("t_autocicd_userinfo")
public class UserInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userRole;
    private Date lastUpdateDate;
}
