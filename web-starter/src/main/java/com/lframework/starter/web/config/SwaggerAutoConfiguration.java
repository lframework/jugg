package com.lframework.starter.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.net.InetAddress;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Swagger Configuration
 */
@Configuration
public class SwaggerAutoConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Bean
  @ConditionalOnMissingBean(OpenAPI.class)
  @ConditionalOnProperty(value = "knife4j.enable", matchIfMissing = true)
  public OpenAPI openAPI(Environment environment) {

    return new OpenAPI().info(new Info()
        .title(environment.getProperty("spring.application.name"))
        .description("Jugg OpenAPI 文档")
        .version("v1.0.0"));
  }

  @Bean
  @ConditionalOnProperty(value = "knife4j.enable", matchIfMissing = true)
  public SwaggerReadyListener swaggerReadyListener() {

    return new SwaggerReadyListener();
  }

  /**
   * Application启动成功监听器
   */
  @Slf4j
  public static class SwaggerReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
      Environment environment = applicationReadyEvent.getApplicationContext().getEnvironment();
      String applicationName = environment.getProperty("spring.application.name");
      String serverPort = environment.getProperty("server.port");
      String hostAddress = InetAddress.getLocalHost().getHostAddress();

      log.info("\n----------------------------------------------------------\n\t"
              + "Application '{}' 已启动！ 访问地址：\n\t" + "Local： \thttp://localhost:{}\n\t"
              + "External： \thttp://{}:{}\n\t" + "Doc: \thttp://{}:{}/doc.html\n"
              + "----------------------------------------------------------",
          applicationName,
          serverPort,
          hostAddress, serverPort,
          hostAddress, serverPort);
    }
  }
}
