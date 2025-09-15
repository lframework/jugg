package com.lframework.starter.web.core.utils;

import org.springframework.cache.CacheManager;

/**
 * 缓存工具类
 * 基于SpringCache提供缓存操作功能，支持缓存的增删改查
 * 包括缓存获取、存储、删除等基本操作
 *
 * @author lframework@163.com
 */
public class CacheUtil {

  private static CacheManager cacheManager = ApplicationUtil.getBean(CacheManager.class);

  /**
   * 获取缓存值
   * 从指定缓存中获取指定键的值
   *
   * @param cacheName 缓存名称，不能为null
   * @param key 缓存键，不能为null
   * @param clazz 值类型，不能为null
   * @param <T> 值类型
   * @return 缓存值，如果不存在则返回null
   */
  public static <T> T get(String cacheName, Object key, Class<T> clazz) {

    return cacheManager.getCache(cacheName).get(key, clazz);
  }

  /**
   * 存储缓存值
   * 将键值对存储到指定缓存中
   *
   * @param cacheName 缓存名称，不能为null
   * @param key 缓存键，不能为null
   * @param value 缓存值，可以为null
   */
  public static void put(String cacheName, Object key, Object value) {

    cacheManager.getCache(cacheName).put(key, value);
  }

  /**
   * 存储非空缓存值
   * 只有当值不为空时才存储到缓存中
   *
   * @param cacheName 缓存名称，不能为null
   * @param key 缓存键，不能为null
   * @param value 缓存值，如果为空则不存储
   */
  public static void putIfNotEmpty(String cacheName, Object key, Object value) {

    if (ValidateUtil.isEmpty(value)) {
      return;
    }

    put(cacheName, key, value);
  }

  /**
   * 删除缓存值
   * 从指定缓存中删除指定键的值
   *
   * @param cacheName 缓存名称，不能为null
   * @param key 缓存键，不能为null
   */
  public static void evict(String cacheName, Object key) {

    cacheManager.getCache(cacheName).evict(key);
  }
}
