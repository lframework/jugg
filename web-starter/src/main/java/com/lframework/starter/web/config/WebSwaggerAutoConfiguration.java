package com.lframework.starter.web.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class WebSwaggerAutoConfiguration {

  @Bean("webApiDocket")
  public Docket webApiDocket(ApiInfo info, OpenApiExtensionResolver openApiExtensionResolver) {

    // 除了描述 其他全与info保持一致
    ApiInfo apiInfo = new ApiInfo(info.getTitle(), "系统功能模块", info.getVersion(),
        info.getTermsOfServiceUrl(),
        info.getContact(), info.getLicense(), info.getLicenseUrl(), info.getVendorExtensions());

    Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).groupName("系统功能模块")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.lframework.starter.web.inner"))
        .paths(PathSelectors.any())
        .build()
        .extensions(openApiExtensionResolver.buildSettingExtensions());
    return docket;
  }

  @Bean("genApiDocket")
  public Docket genApiDocket(ApiInfo info, OpenApiExtensionResolver openApiExtensionResolver) {

    // 除了描述 其他全与info保持一致
    ApiInfo apiInfo = new ApiInfo(info.getTitle(), "代码生成模块", info.getVersion(),
        info.getTermsOfServiceUrl(),
        info.getContact(), info.getLicense(), info.getLicenseUrl(), info.getVendorExtensions());

    Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).groupName("代码生成模块")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.lframework.starter.web.gen"))
        .paths(PathSelectors.any())
        .build()
        .extensions(openApiExtensionResolver.buildSettingExtensions());
    return docket;
  }
}
