package com.lframework.starter.web.config;

import com.lframework.starter.web.core.utils.ApplicationUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class WebCommonAutoConfiguration {

  @Bean
  public static ApplicationListener<ContextRefreshedEvent> applicationContextInitializer() {
    return event -> ApplicationUtil.setApplicationContext(event.getApplicationContext());
  }
}
