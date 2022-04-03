package com.lframework.starter.security.jwt.config;

import com.lframework.starter.security.impl.AbstractMenuServiceImpl;
import com.lframework.starter.security.jwt.impl.DefaultMenuServiceImpl;
import com.lframework.starter.web.service.IMenuService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtDBSecurityConfiguration {

  @Bean
  @ConditionalOnMissingBean(IMenuService.class)
  public IMenuService getMenuService() {

    AbstractMenuServiceImpl menuService = new DefaultMenuServiceImpl();
    return menuService;
  }
}
