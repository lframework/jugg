package com.lframework.starter.bpm.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BpmSwaggerAutoConfiguration {

  @Bean("bpmApi")
  public GroupedOpenApi bpmApi() {

    return GroupedOpenApi.builder().group("审批流程模块")
        .packagesToScan("com.lframework.starter.bpm").build();
  }
}
