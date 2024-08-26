package com.arkmon.autocicd.domains.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class YapiAutoTestRspDTO {
    private Message message;
    private String runTime;
    private int numbs;
    List<AutoTestDetail> list;

    @Data
    public static class Message {
        private String msg;
        private int len;
        private int successNum;
        private int failedNum;
    }

    @Data
    public static class AutoTestDetail {
        private int id;
        private String name;
        private String path;
        private int code;
        private List<ValidRes> validRes;
        private int status;
        private String method;
        private Object data;
        private Object headers;
        private Object res_header;
        private Object res_body;
        private Object params;

        @Data
        public static class ValidRes {
            private String message;
        }
    }
}
