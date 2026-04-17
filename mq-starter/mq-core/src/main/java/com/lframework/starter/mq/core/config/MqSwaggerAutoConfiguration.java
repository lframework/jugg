package com.lframework.starter.mq.core.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqSwaggerAutoConfiguration {

  @Bean("mqApi")
  public GroupedOpenApi mqApi() {

    return GroupedOpenApi.builder().group("消息队列模块")
        .packagesToScan("com.lframework.starter.mq.core").build();
  }
}
