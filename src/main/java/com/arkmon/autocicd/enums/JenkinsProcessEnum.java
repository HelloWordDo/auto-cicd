package com.arkmon.autocicd.enums;

import lombok.Getter;

@Getter
public enum JenkinsProcessEnum {

    /**
     * 准备开始
     */
    READY(0, "ready", "准备开始"),
    /**
     * 部署中
     */
    PROCESSING(1, "processing", "部署中"),
    /**
     * 部署成功
     */
    SUCCESS(2, "succeed", "部署成功"),
    /**
     * 部署失败
     */
    FAILURE(3, "failed", "部署失败"),
    /**
     * 部署终止
     */
    TERMINATION(4, "terminated", "部署终止"),
    /**
     * 部署终止
     */
    TIMEOUT(4, "timeout", "部署超时"),
    ;

    private int code;
    private String name;
    private String desc;

    JenkinsProcessEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
