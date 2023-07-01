package com.lframework.starter.web.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class LettuceAutoConfiguration implements InitializingBean {

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  @Override
  public void afterPropertiesSet() throws Exception {
    if (redisConnectionFactory instanceof LettuceConnectionFactory) {
      LettuceConnectionFactory c = (LettuceConnectionFactory) redisConnectionFactory;
      c.setValidateConnection(true);
    }
  }
}
