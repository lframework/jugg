package com.lframework.starter.cloud.config;

import com.lframework.starter.cloud.components.trace.CloudTraceBuilder;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilderWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudBeanAutoConfiguration {

  @Bean
  public CloudTraceBuilder cloudTraceBuilder() {
    return new CloudTraceBuilder();
  }

  @Bean
  public ApiInvokeResultBuilderWrapper apiInvokeResultBuilderWrapper() {
    return new ApiInvokeResultBuilderWrapper();
  }
}
