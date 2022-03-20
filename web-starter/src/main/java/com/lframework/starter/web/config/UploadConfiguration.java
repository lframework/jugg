package com.lframework.starter.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Configuration
public class UploadConfiguration implements WebMvcConfigurer {

    @Value("${upload.url}")
    private String url;

    @Value("${upload.location}")
    private String location;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(url.endsWith("/") ? url + "**" : url + "/**").addResourceLocations("file:" + (location.endsWith(File.separator) ? location: location + File.separator));
    }
}
