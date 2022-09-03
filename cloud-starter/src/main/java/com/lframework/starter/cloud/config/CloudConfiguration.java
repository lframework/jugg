package com.lframework.starter.cloud.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudConfiguration {

  @Bean
  public Retryer retryer() {

    // 不进行重试
    return Retryer.NEVER_RETRY;
  }
}
