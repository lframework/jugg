package com.lframework.starter.web.service;

import com.lframework.starter.web.utils.ApplicationUtil;

/**
 * Service基类
 *
 * @author zmj
 */
public interface BaseService {

  default <T> T getThis(Class<T> clazz) {

    return ApplicationUtil.getBean(clazz);
  }

  /**
   * 根据Key清除缓存
   *
   * @param key
   */
  default void cleanCacheByKey(String key) {

  }
}
