package com.lframework.starter.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lframework.starter.web.utils.ApplicationUtil;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Service基类
 *
 * @author zmj
 */
public interface BaseMpService<T> extends IService<T> {

  default <S> S getThis(Class<S> clazz) {

    return ApplicationUtil.getBean(clazz);
  }

  /**
   * 根据Key清除缓存
   *
   * @param key
   */
  default void cleanCacheByKey(Serializable key) {

  }

  /**
   * 批量根据Key清除缓存
   *
   * @param keys
   */
  default void cleanCacheByKeys(Serializable... keys) {
    BaseMpService<T> thisService = getThis(getClass());
    Arrays.stream(keys).forEach(thisService::cleanCacheByKey);
  }
}
