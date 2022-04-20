package com.lframework.starter.bpm.config;

import com.lframework.starter.bpm.impl.BpmTodoTaskServiceImpl;
import com.lframework.starter.mybatis.service.message.ITodoTaskService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "bpm.enabled", matchIfMissing = true)
public class BpmConfiguration {

  @Bean
  public ITodoTaskService todoTaskService() {

    return new BpmTodoTaskServiceImpl();
  }
}
