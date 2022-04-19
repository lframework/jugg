package com.lframework.starter.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lframework.starter.web.utils.ApplicationUtil;

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
  default void cleanCacheByKey(String key) {

  }
}
