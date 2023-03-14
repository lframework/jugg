package com.lframework.starter.cloud.config;

import com.lframework.starter.common.constants.StringPool;
import feign.Retryer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class CloudConfiguration {

  @Autowired
  private ObjectFactory<HttpMessageConverters> messageConverters;

  @Bean
  public Retryer retryer() {

    // 不进行重试
    return Retryer.NEVER_RETRY;
  }

  @Bean
  public FeignFormatterRegistrar localDataTimeFormatRegister() {
    return registry -> registry.addConverter(new Converter<LocalDateTime, String>() {
      @Override
      public String convert(LocalDateTime source) {
        return source.format(DateTimeFormatter.ofPattern(StringPool.DATE_TIME_PATTERN));
      }
    });
  }

  @Bean
  public FeignFormatterRegistrar localDataFormatRegister() {
    return registry -> registry.addConverter(new Converter<LocalDate, String>() {
      @Override
      public String convert(LocalDate source) {
        return source.format(DateTimeFormatter.ofPattern(StringPool.DATE_PATTERN));
      }
    });
  }

  @Bean
  public FeignFormatterRegistrar localTimeFormatRegister() {
    return registry -> registry.addConverter(new Converter<LocalTime, String>() {
      @Override
      public String convert(LocalTime source) {
        return source.format(DateTimeFormatter.ofPattern(StringPool.TIME_PATTERN));
      }
    });
  }
}
