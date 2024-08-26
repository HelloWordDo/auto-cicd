package com.arkmon.autocicd.domains.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_autocicd_release_version")
@Builder
public class ReleaseVersion {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String releaseVersion;
    private String releaseVersionOptional;
    private Date creationDate;
    private Date lastUpdateDate;
}
