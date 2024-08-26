package com.arkmon.autocicd.enums;


import lombok.Getter;

@Getter
public enum NotificationEnum {

    EMAIL("0", "email", "电子邮件"),
    DING_TALK("1", "dingTalk", "钉钉"),
    ;

    private String code;
    private String name;
    private String desc;

    NotificationEnum(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
