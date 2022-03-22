package com.lframework.starter.web.config;

import com.aliyun.dysmsapi20170525.Client;
import com.lframework.common.utils.AliSmsUtil;
import com.lframework.starter.web.impl.AliSmsServiceImpl;
import com.lframework.starter.web.service.IAliSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AliSmsProperties.class)
public class AliSmsConfiguration {

  @Bean
  @ConditionalOnMissingBean(IAliSmsService.class)
  public IAliSmsService getAliSmsService(AliSmsProperties properties) throws Exception {

    Client client = AliSmsUtil
        .createClient(properties.getAccessKeyId(), properties.getAccessKeySecret());
    return new AliSmsServiceImpl(client);
  }
}
