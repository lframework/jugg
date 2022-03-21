package com.lframework.starter.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sms.ali")
public class AliSmsProperties {

    /**
     * 地域
     */
    private String region;

    private String accessKeyId;

    private String accessKeySecret;
}
