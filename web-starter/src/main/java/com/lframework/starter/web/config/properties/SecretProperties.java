package com.lframework.starter.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jugg.secret")
public class SecretProperties {

    /**
     * 秘钥
     */
    private String key;
}
