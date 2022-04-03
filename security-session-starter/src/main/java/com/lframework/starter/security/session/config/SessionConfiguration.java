package com.lframework.starter.security.session.config;

import com.lframework.common.constants.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * Session配置
 *
 * @author zmj
 */
@Slf4j
@Configuration
public class SessionConfiguration {

  @Bean
  public HttpSessionIdResolver headerHttpSessionIdResolver() {

    return new XHttpSessionIdResolver(StringPool.HEADER_NAME_SESSION_ID);
  }
}
