package com.lframework.starter.web.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.IdWorker;
import com.lframework.common.utils.StringUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置
 *
 * @author zmj
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Value("${worker-id:1}")
  private Long workerId;

  @Value("${center-id:1}")
  private Long centerId;

  @Bean
  @ConditionalOnMissingBean(CorsFilter.class)
  public CorsFilter getCorsFilter() {

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    // 设置访问源地址
    config.addAllowedOrigin("*");
    // 设置访问源请求头
    config.addAllowedHeader("*");
    // 设置访问源请求方法
    config.addAllowedMethod("*");
    // 对接口配置跨域设置
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Bean
  public Converter<String, LocalDateTime> localDateTimeConvert() {

    return new Converter<String, LocalDateTime>() {
      @Override
      public LocalDateTime convert(String source) {

        if (StringUtil.isBlank(source)) {
          return null;
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern(StringPool.DATE_TIME_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(source, df);
        return dateTime;
      }
    };
  }

  @Bean
  public Converter<String, LocalDate> localDateConvert() {

    return new Converter<String, LocalDate>() {
      @Override
      public LocalDate convert(String source) {

        if (StringUtil.isBlank(source)) {
          return null;
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern(StringPool.DATE_PATTERN);
        LocalDate date = LocalDate.parse(source, df);
        return date;
      }
    };
  }

  @Bean
  public Converter<String, LocalTime> localTimeConvert() {

    return new Converter<String, LocalTime>() {
      @Override
      public LocalTime convert(String source) {

        if (StringUtil.isBlank(source)) {
          return null;
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern(StringPool.TIME_PATTERN);
        LocalTime time = LocalTime.parse(source, df);
        return time;
      }
    };
  }

  @Bean
  public ObjectMapper getObjectMapper(Jackson2ObjectMapperBuilder builder) {

    ObjectMapper om = builder.build().configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
        .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule());
    return om;
  }

  @Bean
  public IdWorker getIdWorker() {

    return new IdWorker(workerId, centerId);
  }
}
