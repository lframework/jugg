package com.lframework.starter.web.config;

import com.lframework.starter.web.config.properties.KaptchaProperties;
import com.lframework.starter.web.core.components.captcha.CaptchaProducer;
import com.lframework.starter.web.core.components.captcha.DefaultCaptchaProducer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 验证码配置
 *
 * @author zmj
 */
@Configuration
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaAutoConfiguration {

  @Bean
  @Primary
  public CaptchaProducer captchaProducer(KaptchaProperties properties) {
    return new DefaultCaptchaProducer(properties);
  }
}
