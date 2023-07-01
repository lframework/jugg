package com.lframework.starter.web.config;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.config.properties.CacheProperties;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class CacheAutoConfiguration extends CachingConfigurerSupport {

  @Autowired
  private RedisTemplate redisTemplate;

  @Bean
  public RedisCacheWriter writer() {
    return RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
  }

  @Bean
  public CacheManager cacheManager(CacheProperties properties) {
    Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
    if (!CollectionUtil.isEmpty(properties.getRegions())) {
      properties.getRegions().forEach((k, v) -> {
        configurationMap.put(k, RedisCacheConfiguration
            .defaultCacheConfig().entryTtl(Duration.ofSeconds(v)));
      });
    }

    return RedisCacheManager.builder(writer())
        .initialCacheNames(configurationMap.keySet())
        .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(properties.getTtl())))
        .withInitialCacheConfigurations(configurationMap)
        .build();
  }
}
