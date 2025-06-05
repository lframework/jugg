package com.lframework.starter.web.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import com.lframework.starter.web.core.components.security.PermitAllService;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

  @Autowired
  private PermitAllService permitAllService;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**")
        .excludePathPatterns(
            permitAllService.getUrls().stream().map(Entry::getValue).collect(Collectors.toList()));
  }
}
