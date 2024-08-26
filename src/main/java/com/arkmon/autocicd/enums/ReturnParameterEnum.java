package com.arkmon.autocicd.enums;

import lombok.Getter;

/**
 * @author X.J
 * @date 2021/3/1
 */
@Getter
public enum ReturnParameterEnum {
    SUCCESS("10000", "SUCCESS", "请求成功"),
    FAILED("-1", "FAILED", "请求失败"),

    ;

    private String code;
    private String name;
    private String desc;

    ReturnParameterEnum(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
