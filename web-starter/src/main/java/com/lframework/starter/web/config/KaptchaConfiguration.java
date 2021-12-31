package com.lframework.starter.web.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 *
 * @author zmj
 */
@Configuration
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaConfiguration {

    @Bean
    public Producer getProducer(KaptchaProperties properties) {

        Config config = new Config(properties.props());

        return config.getProducerImpl();
    }
}
