package com.arkmon.autocicd.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * application.yml配置文件里的custom配置的映射对象
 *
 * @author X.J
 * @date 2021/2/24
 */

@ConfigurationProperties(prefix = "custom")
@Configuration
@Data
public class CustomsConfig {
    private Jenkins jenkins;
    private Yapi yapi;
    private DingTalk dingTalk;
    private Minio minio;

    @Data
    public static class Jenkins {
        private String user;
        private String token;
        private String url;
    }

    @Data
    public static class Yapi {
        private String url;
    }

    @Data
    public static class DingTalk {
        private String webHookToken;
        private String atAll;
    }

    @Data
    public static class Minio {
        private String url;
        private String accessKey;
        private String secretKey;
        private String bucket;
    }
}
