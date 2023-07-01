package com.lframework.starter.web.config;

import com.lframework.starter.web.components.CaptchaValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisBeanAutoConfiguration {

  @Bean
  public CaptchaValidator captchaValidator() {
    return new CaptchaValidator();
  }
}
