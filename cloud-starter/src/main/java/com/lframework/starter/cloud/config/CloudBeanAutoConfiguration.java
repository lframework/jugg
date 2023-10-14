package com.lframework.starter.cloud.config;

import com.lframework.starter.cloud.resp.ApiInvokeResultErrorBuilderWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudBeanAutoConfiguration {

  @Bean
  public ApiInvokeResultErrorBuilderWrapper apiInvokeResultBuilderWrapper() {
    return new ApiInvokeResultErrorBuilderWrapper();
  }
}
