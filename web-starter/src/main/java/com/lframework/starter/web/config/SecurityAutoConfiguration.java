package com.lframework.starter.web.config;

import com.lframework.starter.web.core.components.security.PermitAllService;
import com.lframework.starter.web.core.components.tenant.TenantInterceptor;
import com.lframework.starter.web.core.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityAutoConfiguration implements WebMvcConfigurer {

  @Autowired
  private PermitAllService permitAllService;

  @Autowired
  private TenantInterceptor tenantInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(new LoginInterceptor(permitAllService));
    registry.addInterceptor(tenantInterceptor);
  }
}
