package com.lframework.starter.web.config;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UploadConfiguration implements WebMvcConfigurer {

  @Value("${upload.url}")
  private String url;

  @Value("${upload.location}")
  private String location;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    registry.addResourceHandler(url.endsWith("/") ? url + "**" : url + "/**").addResourceLocations(
        "file:" + (location.endsWith(File.separator) ? location : location + File.separator));
  }
}
