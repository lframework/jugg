package com.lframework.starter.web.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.lframework.starter.web.components.redis.RedisHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Configuration
public class RedisAutoConfiguration {

  @Bean
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {

    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);

    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
        Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

    om.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
    om.findAndRegisterModules();

    om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    jackson2JsonRedisSerializer.setObjectMapper(om);

    template.setKeySerializer(new GenericToStringSerializer<>(Object.class));
    template.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));

    template.setValueSerializer(new JdkSerializationRedisSerializer());
    template.setHashValueSerializer(new JdkSerializationRedisSerializer());

    template.afterPropertiesSet();
    return template;
  }

  @Bean
  public RedisHandler redisHandler() {
    return new RedisHandler();
  }
}
