package com.lframework.starter.web.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import com.lframework.starter.web.components.security.PermitAllService;
import com.lframework.starter.web.filters.LogFilter;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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

  @Bean
  public LogFilter logFilter() {
    return new LogFilter();
  }

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public FilterRegistrationBean<LogFilter> logFilterRegistrationBean() {
    FilterRegistrationBean<LogFilter> logFilter = new FilterRegistrationBean<>();
    logFilter.addUrlPatterns("/*");
    logFilter.setFilter(logFilter());
    return logFilter;
  }
}
