package com.lframework.starter.web.config;

import com.lframework.starter.web.components.security.LoginInterceptor;
import com.lframework.starter.web.components.security.PermitAllService;
import com.lframework.starter.web.components.tenant.DefaultTenantInterceptor;
import com.lframework.starter.web.components.tenant.TenantInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityAutoConfiguration implements WebMvcConfigurer {

  @Autowired
  private PermitAllService permitAllService;

  @Autowired
  private TenantInterceptor tenantInterceptor;

  @Bean
  @ConditionalOnProperty(prefix = "jugg.tenant", value = "enabled", havingValue = "false")
  public TenantInterceptor tenantInterceptor() {
    return new DefaultTenantInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(new LoginInterceptor(permitAllService));
    registry.addInterceptor(tenantInterceptor);
  }
}
