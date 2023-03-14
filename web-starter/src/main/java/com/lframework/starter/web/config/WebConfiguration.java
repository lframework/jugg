package com.lframework.starter.web.config;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;
import static cn.hutool.core.date.DatePattern.NORM_DATE_PATTERN;
import static cn.hutool.core.date.DatePattern.NORM_TIME_PATTERN;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jdk8.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.IdWorker;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.security.CheckPermissionHandler;
import com.lframework.starter.web.components.security.CheckPermissionHandlerImpl;
import com.lframework.starter.web.components.security.PermitAllService;
import com.lframework.starter.web.components.trace.DefaultTraceBuilder;
import com.lframework.starter.web.components.trace.TraceBuilder;
import com.lframework.starter.web.sign.CheckSignHandler;
import com.lframework.starter.web.sign.DefaultCheckSignHandler;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Web配置
 *
 * @author zmj
 */
@Configuration
public class WebConfiguration {

  @Value("${worker-id:-1}")
  private Long workerId;

  @Value("${center-id:-1}")
  private Long centerId;

  @Autowired
  private PermitAllService permitAllService;

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
        .registerModule(new JavaTimeModule()).registerModule(new JavaLocalDateTimeModule());
    return om;
  }

  class JavaLocalDateTimeModule extends SimpleModule {

    public JavaLocalDateTimeModule() {
      super(PackageVersion.VERSION);
      this.addSerializer(LocalDateTime.class,
          new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(
              NORM_DATETIME_PATTERN)));
      this.addSerializer(LocalDate.class,
          new LocalDateSerializer(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN)));
      this.addSerializer(LocalTime.class,
          new LocalTimeSerializer(DateTimeFormatter.ofPattern(NORM_TIME_PATTERN)));
      this.addDeserializer(LocalDateTime.class,
          new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN)));
      this.addDeserializer(LocalDate.class,
          new LocalDateDeserializer(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN)));
      this.addDeserializer(LocalTime.class,
          new LocalTimeDeserializer(DateTimeFormatter.ofPattern(NORM_TIME_PATTERN)));

    }
  }

  @Bean
  public IdWorker getIdWorker() {

    if (workerId <= 0 || centerId <= 0) {
      return new IdWorker();
    }
    return new IdWorker(workerId, centerId);
  }

  @Bean
  @ConditionalOnMissingBean(PermitAllService.class)
  public PermitAllService permitAllService() {

    return new PermitAllService();
  }

  @Bean("permission")
  @ConditionalOnMissingBean(CheckPermissionHandler.class)
  public CheckPermissionHandler checkPermissionHandler() {

    return new CheckPermissionHandlerImpl();
  }

  @Bean
  @ConditionalOnMissingBean(TraceBuilder.class)
  public TraceBuilder getTraceBuilder() {
    return new DefaultTraceBuilder();
  }

  @Bean
  @ConditionalOnMissingBean(CheckSignHandler.class)
  public CheckSignHandler getCheckSignHandler() {
    return new DefaultCheckSignHandler();
  }
}
