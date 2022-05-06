package com.lframework.starter.web.utils;

import org.springframework.cache.annotation.CachingConfigurer;

/**
 * 缓存工具类 基于SpringCache
 */
public class CacheUtil {

  private static final CachingConfigurer configurer = ApplicationUtil.getBean(CachingConfigurer.class);

  public static <T> T get(String cacheName, Object key, Class<T> clazz) {

    return configurer.cacheManager().getCache(cacheName).get(key, clazz);
  }

  public static void put(String cacheName, Object key, Object value) {

    configurer.cacheManager().getCache(cacheName).put(key, value);
  }

  public static void putIfNotEmpty(String cacheName, Object key, Object value) {

    if (ValidateUtil.isEmpty(value)) {
      return;
    }

    put(cacheName, key, value);
  }

  public static void evict(String cacheName, Object key) {

    configurer.cacheManager().getCache(cacheName).evict(key);
  }
}
