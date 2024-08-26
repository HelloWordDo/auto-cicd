package com.arkmon.autocicd.domains.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_autocicd_deploy_denied_window")
public class DeployDeniedWindow {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String projectName;
    private String environment;
    private Date deniedStart;
    private Date deniedEnd;
    private Date lastUpdateDate;
}
