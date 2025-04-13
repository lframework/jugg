package com.lframework.starter.web.config;

import com.lframework.starter.web.aop.ControllerAspector;
import com.lframework.starter.web.aop.OpenApiAspect;
import com.lframework.starter.web.aop.PermissionAspect;
import com.lframework.starter.web.components.cache.CacheVariables;
import com.lframework.starter.web.components.generator.handler.impl.CurrentDateTimeRuleGenerateCodeHandler;
import com.lframework.starter.web.components.generator.handler.impl.CustomRandomStrGenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.handler.impl.FlowGenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.handler.impl.SnowFlakeGenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.handler.impl.StaticStrGenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.handler.impl.UUIDGenerateCodeRuleHandler;
import com.lframework.starter.web.components.security.UserTokenResolver;
import com.lframework.starter.web.components.upload.handler.SecurityUploadHandler;
import com.lframework.starter.web.components.upload.handler.UploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.CosSecurityUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.CosUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.LocalSecurityUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.LocalUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.ObsSecurityUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.ObsUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.OssSecurityUploadHandler;
import com.lframework.starter.web.components.upload.handler.impl.OssUploadHandler;
import com.lframework.starter.web.resp.InvokeResultErrorBuilderWrapper;
import com.lframework.starter.web.resp.ResponseErrorBuilder;
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
  public SecurityUploadHandler ossSecurityUploadHandler() {
    return new OssSecurityUploadHandler();
  }

  @Bean
  public SecurityUploadHandler cosSecurityUploadHandler() {
    return new CosSecurityUploadHandler();
  }

  @Bean
  public SecurityUploadHandler localSecurityUploadHandler() {
    return new LocalSecurityUploadHandler();
  }

  @Bean
  public SecurityUploadHandler obsSecurityUploadHandler() {
    return new ObsSecurityUploadHandler();
  }

  @Bean
  public ResponseErrorBuilder invokeResultBuilderWrapper() {
    return new InvokeResultErrorBuilderWrapper();
  }

  @Bean
  public CheckSignFactory defaultCheckSignFactory() {
    return new DefaultCheckSignFactory();
  }

  @Bean("cacheVariables")
  public CacheVariables cacheVariables() {
    return new CacheVariables();
  }

  @Bean
  public UserTokenResolver userTokenResolver() {
    return new UserTokenResolver();
  }

  @Bean
  public CurrentDateTimeRuleGenerateCodeHandler currentDateTimeRuleGenerateHandler() {
    return new CurrentDateTimeRuleGenerateCodeHandler();
  }

  @Bean
  public CustomRandomStrGenerateCodeRuleHandler customRandomStrGenerateRuleHandler() {
    return new CustomRandomStrGenerateCodeRuleHandler();
  }

  @Bean
  public FlowGenerateCodeRuleHandler flowGenerateRuleHandler() {
    return new FlowGenerateCodeRuleHandler();
  }

  @Bean
  public SnowFlakeGenerateCodeRuleHandler snowFlakeGenerateRuleHandler() {
    return new SnowFlakeGenerateCodeRuleHandler();
  }

  @Bean
  public StaticStrGenerateCodeRuleHandler staticStrGenerateRuleHandler() {
    return new StaticStrGenerateCodeRuleHandler();
  }

  @Bean
  public UUIDGenerateCodeRuleHandler uuidGenerateRuleHandler() {
    return new UUIDGenerateCodeRuleHandler();
  }
}
