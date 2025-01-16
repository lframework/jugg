package com.lframework.starter.web.config;

import com.lframework.starter.web.utils.ApplicationUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebCommonAutoConfiguration {

  @Bean
  public ApplicationUtil applicationUtil() {
    return new ApplicationUtil();
  }
}
