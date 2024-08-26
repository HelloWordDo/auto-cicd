package com.arkmon.autocicd.enums;

import lombok.Getter;

/**
 * @author X.J
 * @date 2021/2/7
 */
@Getter
public enum EnvironmentEnum {
    /**
     * sit 环境
     */
    DEPLOY_DEV(0, "dev", "dev 环境"),
    /**
     * sit 环境
     */
    DEPLOY_SIT(1, "sit", "sit 环境"),
    /**
     * uat 环境
     */
    DEPLOY_UAT(2, "uat", "uat 环境"),
    /**
     * prod 环境
     */
    DEPLOY_PROD(3, "prod", "prod 生产环境"),
    /**
     * sit 和 uat 环境
     */
    DEPLOY_SIT_UAT(100, "sit_uat", "sit 和 uat 环境"),
    ;

    private int code;
    private String name;
    private String desc;

    EnvironmentEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
