package com.arkmon.autocicd.enums;

import lombok.Getter;

/**
 * @author X.J
 * @date 2021/3/1
 */
@Getter
public enum UserRoleEnum {
    ADMIN("0", "admin", "管理员权限，最高权限"),
    OWNER("1", "owner", "各自服务的最高权限"),
    GUEST("2", "guest", "无权限")
    ;

    private String code;
    private String name;
    private String desc;

    UserRoleEnum(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
