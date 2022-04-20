package com.lframework.starter.security.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.lframework.starter.security.components.CheckPermissionHandler;
import com.lframework.starter.security.components.CheckPermissionHandlerImpl;
import com.lframework.starter.security.components.LoginInterceptor;
import com.lframework.starter.web.components.security.PermitAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

  @Autowired
  private PermitAllService permitAllService;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(new LoginInterceptor(permitAllService));
  }

  @Bean("securityApiDocket")
  public Docket securityAPiDocket(ApiInfo info, OpenApiExtensionResolver openApiExtensionResolver) {

    // 除了描述 其他全与info保持一致
    ApiInfo apiInfo = new ApiInfo(info.getTitle(), "用户认证鉴权模块以及公共接口", info.getVersion(),
        info.getTermsOfServiceUrl(),
        info.getContact(), info.getLicense(), info.getLicenseUrl(), info.getVendorExtensions());

    Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo)
        .groupName("用户认证鉴权以及公共接口").select()
        .apis(RequestHandlerSelectors.basePackage("com.lframework.starter.security"))
        .paths(PathSelectors.any())
        .build().extensions(openApiExtensionResolver.buildSettingExtensions());
    return docket;
  }

  @Bean("permission")
  @ConditionalOnMissingBean(CheckPermissionHandler.class)
  public CheckPermissionHandler checkPermissionHandler() {

    return new CheckPermissionHandlerImpl();
  }
}
