package com.lframework.starter.web.core.service;

import java.io.Serializable;

public interface BaseService {

  /**
   * 根据Key清除缓存
   *
   * @param key
   */
  default void cleanCacheByKey(Serializable key) {

  }
}
