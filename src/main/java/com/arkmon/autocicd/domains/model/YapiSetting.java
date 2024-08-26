package com.arkmon.autocicd.domains.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_yapi_setting")
@Builder
public class YapiSetting {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String projectName;
    private String serviceName;
    private String projectEnv;
    private String yapiProjectId;
    private String yapiProjectToken;
    private String yapiEnv;
    private String yapiEnvId;
    private Date lastUpdateDate;
}
