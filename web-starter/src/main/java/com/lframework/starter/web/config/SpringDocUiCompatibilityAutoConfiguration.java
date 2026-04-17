package com.lframework.starter.web.config;

import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.webmvc.ui.SwaggerIndexTransformer;
import org.springdoc.webmvc.ui.SwaggerResourceResolver;
import org.springdoc.webmvc.ui.SwaggerWebMvcConfigurer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@AutoConfiguration(beforeName = "org.springdoc.webmvc.ui.SwaggerConfig")
@ConditionalOnClass(SwaggerWebMvcConfigurer.class)
@ConditionalOnProperty(value = "knife4j.enable", matchIfMissing = true)
public class SpringDocUiCompatibilityAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(SwaggerWebMvcConfigurer.class)
  public SwaggerWebMvcConfigurer swaggerWebMvcConfigurer(
      SwaggerUiConfigProperties swaggerUiConfigProperties,
      WebProperties springWebProperties,
      WebMvcProperties springWebMvcProperties,
      SwaggerIndexTransformer swaggerIndexTransformer,
      SwaggerResourceResolver swaggerResourceResolver,
      SwaggerWelcomeCommon swaggerWelcomeCommon) {

    return new NoOpSwaggerWebMvcConfigurer(swaggerUiConfigProperties, springWebProperties,
        springWebMvcProperties, swaggerIndexTransformer, swaggerResourceResolver,
        swaggerWelcomeCommon);
  }

  static class NoOpSwaggerWebMvcConfigurer extends SwaggerWebMvcConfigurer {

    NoOpSwaggerWebMvcConfigurer(SwaggerUiConfigProperties swaggerUiConfigProperties,
        WebProperties springWebProperties,
        WebMvcProperties springWebMvcProperties,
        SwaggerIndexTransformer swaggerIndexTransformer,
        SwaggerResourceResolver swaggerResourceResolver,
        SwaggerWelcomeCommon swaggerWelcomeCommon) {

      super(swaggerUiConfigProperties, springWebProperties, springWebMvcProperties,
          swaggerIndexTransformer, swaggerResourceResolver, swaggerWelcomeCommon);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      // Knife4j uses doc.html and /webjars/**, which are mapped in SwaggerAutoConfiguration.
    }
  }
}
