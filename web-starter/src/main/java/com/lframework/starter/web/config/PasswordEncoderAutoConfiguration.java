package com.lframework.starter.web.config;

import com.lframework.starter.web.core.components.security.PasswordEncoderWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(PasswordEncoder.class)
  public PasswordEncoder getPasswordEncoder() {

    return new BCryptPasswordEncoder();
  }

  @Bean
  public PasswordEncoderWrapper passwordEncoderWrapper() {
    return new PasswordEncoderWrapper();
  }
}
