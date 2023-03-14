package com.lframework.starter.web.utils;

import com.lframework.starter.web.common.utils.ApplicationUtil;
import org.springframework.cache.CacheManager;

/**
 * 缓存工具类 基于SpringCache
 */
public class CacheUtil {

  private static CacheManager cacheManager = ApplicationUtil.getBean(CacheManager.class);

  public static <T> T get(String cacheName, Object key, Class<T> clazz) {

    return cacheManager.getCache(cacheName).get(key, clazz);
  }

  public static void put(String cacheName, Object key, Object value) {

    cacheManager.getCache(cacheName).put(key, value);
  }

  public static void putIfNotEmpty(String cacheName, Object key, Object value) {

    if (ValidateUtil.isEmpty(value)) {
      return;
    }

    put(cacheName, key, value);
  }

  public static void evict(String cacheName, Object key) {

    cacheManager.getCache(cacheName).evict(key);
  }
}
