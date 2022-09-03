package com.lframework.starter.cloud.config;

import com.lframework.starter.web.components.security.LoginInterceptor;
import com.lframework.starter.web.components.security.PermitAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录态拦截下放到各服务应用
 */
@Configuration
public class CloudSecurityConfiguration implements WebMvcConfigurer {

  @Autowired
  private PermitAllService permitAllService;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(new LoginInterceptor(permitAllService));
  }
}