package com.lframework.starter.bpm.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class BpmSwaggerAutoConfiguration {

  @Bean("bpmApiDocket")
  public Docket bpmApiDocket(ApiInfo info, OpenApiExtensionResolver openApiExtensionResolver) {

    // 除了描述 其他全与info保持一致
    ApiInfo apiInfo = new ApiInfo(info.getTitle(), "审批流程模块", info.getVersion(),
        info.getTermsOfServiceUrl(),
        info.getContact(), info.getLicense(), info.getLicenseUrl(), info.getVendorExtensions());

    Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).groupName("审批流程模块")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.lframework.starter.bpm"))
        .paths(PathSelectors.any())
        .build()
        .extensions(openApiExtensionResolver.buildSettingExtensions());
    return docket;
  }
}
