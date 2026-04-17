package com.lframework.starter.web.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSwaggerAutoConfiguration {

  @Bean("webApi")
  public GroupedOpenApi webApi() {

    return GroupedOpenApi.builder().group("系统功能模块")
        .packagesToScan("com.lframework.starter.web.inner").build();
  }
}
