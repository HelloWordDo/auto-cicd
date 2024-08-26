package com.arkmon.autocicd.enums;

import lombok.Getter;

/**
 * @author X.J
 * @date 2021/2/24
 */
@Getter
public enum ProjectNameEnum {
    SVECO("0", "sveco", "CNS3.0 SOP2 上汽大众项目", "CNS3SOP2"),
    FAWMQB("1", "fawmqb", "CNS3.0 SOP2 一汽大众项目", "FAWMQB"),
    ;

    private String code;
    private String name;
    private String desc;
    private String jenkinsProjectName;


    ProjectNameEnum(String code, String name, String desc, String jenkinsProjectName) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.jenkinsProjectName = jenkinsProjectName;
    }
}
