package com.lframework.starter.web.config;

import com.lframework.starter.web.aop.ControllerAspector;
import com.lframework.starter.web.aop.OpenApiAspect;
import com.lframework.starter.web.aop.PermissionAspect;
import com.lframework.starter.web.components.cache.CacheVariables;
import com.lframework.starter.web.components.generator.Generator;
import com.lframework.starter.web.components.generator.impl.DefaultFlowGenerator;
import com.lframework.starter.web.components.generator.impl.DefaultGenerator;
import com.lframework.starter.web.components.generator.impl.DefaultSnowFlakeGenerator;
import com.lframework.starter.web.components.upload.handler.UploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.CosUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.LocalUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.ObsUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.OssUploadHandler;
import com.lframework.starter.web.impl.GenerateCodeServiceImpl;
import com.lframework.starter.web.resp.InvokeResultBuilderWrapper;
import com.lframework.starter.web.resp.ResponseBuilder;
import com.lframework.starter.web.service.GenerateCodeService;
import com.lframework.starter.web.sign.CheckSignFactory;
import com.lframework.starter.web.sign.DefaultCheckSignFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebBeanAutoConfiguration {

  @Bean
  public ControllerAspector controllerAspector() {

    return new ControllerAspector();
  }

  @Bean
  public OpenApiAspect openApiAspect() {
    return new OpenApiAspect();
  }

  @Bean
  public PermissionAspect permissionAspect() {
    return new PermissionAspect();
  }

  @Bean
  public Generator defaultFlowGenerator() {
    return new DefaultFlowGenerator();
  }

  @Bean
  public Generator defaultSnowFlakeGenerator() {
    return new DefaultSnowFlakeGenerator();
  }

  @Bean
  public Generator defaultGenerator() {
    return new DefaultGenerator();
  }

  @Bean
  public UploadHandler cosUploadHandler() {
    return new CosUploadHandler();
  }

  @Bean
  public UploadHandler localUploadHandler() {
    return new LocalUploadHandler();
  }

  @Bean
  public UploadHandler obsUploadHandler() {
    return new ObsUploadHandler();
  }

  @Bean
  public UploadHandler ossUploadHandler() {
    return new OssUploadHandler();
  }

  @Bean
  public GenerateCodeService generateCodeService() {
    return new GenerateCodeServiceImpl();
  }

  @Bean
  public ResponseBuilder invokeResultBuilderWrapper() {
    return new InvokeResultBuilderWrapper();
  }

  @Bean
  public CheckSignFactory defaultCheckSignFactory() {
    return new DefaultCheckSignFactory();
  }

  @Bean("cacheVariables")
  public CacheVariables cacheVariables() {
    return new CacheVariables();
  }
}
