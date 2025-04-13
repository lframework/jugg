package com.lframework.starter.web.config;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.config.properties.SecurityUploadProperties;
import com.lframework.starter.web.config.properties.UploadProperties;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties({UploadProperties.class, SecurityUploadProperties.class})
public class UploadAutoConfiguration implements WebMvcConfigurer {

  @Autowired
  private UploadProperties properties;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    if (StringUtil.isNotBlank(properties.getUrl()) && StringUtil.isNotBlank(
        properties.getLocation())) {

      String url = properties.getUrl();
      String location = properties.getLocation();

      registry.addResourceHandler(url.endsWith("/") ? url + "**" : url + "/**")
          .addResourceLocations(
              "file:" + (location.endsWith(File.separator) ? location : location + File.separator));
    }
  }
}
