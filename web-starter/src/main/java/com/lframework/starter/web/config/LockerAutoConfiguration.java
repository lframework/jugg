package com.lframework.starter.web.config;

import com.lframework.starter.common.locker.LockBuilder;
import com.lframework.starter.web.components.locker.DefaultLockBuilder;
import com.lframework.starter.web.components.locker.LockFactory;
import com.lframework.starter.web.components.redis.locker.RedisLockBuilder;
import com.lframework.starter.web.components.redis.locker.RedisLockConditional;
import com.lframework.starter.web.components.security.UserTokenResolver;
import com.lframework.starter.web.config.properties.LockerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LockerProperties.class)
public class LockerAutoConfiguration {

  @Bean
  @Conditional(RedisLockConditional.class)
  public RedisLockBuilder redisLockBuilder() {
    return new RedisLockBuilder();
  }

  @Bean
  @ConditionalOnMissingBean(LockBuilder.class)
  public LockBuilder getLockBuilder() {

    return new DefaultLockBuilder();
  }

  @Bean
  public LockFactory lockFactory() {
    return new LockFactory();
  }

  @Bean
  public UserTokenResolver userTokenResolver() {
    return new UserTokenResolver();
  }
}
