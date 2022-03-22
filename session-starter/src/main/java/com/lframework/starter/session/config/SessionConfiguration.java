package com.lframework.starter.session.config;

import com.lframework.common.constants.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

/**
 * Session配置
 *
 * @author zmj
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SessionProperties.class)
public class SessionConfiguration {

  @Bean
  public HeaderHttpSessionIdResolver headerHttpSessionIdResolver() {

    return new HeaderHttpSessionIdResolver(StringPool.HEADER_NAME_SESSION_ID);
  }
}
