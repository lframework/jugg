package com.lframework.starter.web.config;

import com.lframework.starter.web.utils.ApplicationUtil;
import java.net.InetAddress;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Swagger Configuration
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
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
      log.info("\n----------------------------------------------------------\n\t"
              + "Application '{}' 已启动！ 访问地址：\n\t" + "Local： \thttp://localhost:{}\n\t"
              + "External： \thttp://{}:{}\n\t" + "Doc: \thttp://{}:{}/doc.html\n"
              + "----------------------------------------------------------",
          ApplicationUtil.getProperty("spring.application.name"),
          ApplicationUtil.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),
          ApplicationUtil.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),
          ApplicationUtil.getProperty("server.port"));
    }
  }
}
