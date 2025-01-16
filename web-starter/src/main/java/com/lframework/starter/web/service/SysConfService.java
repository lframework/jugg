package com.lframework.starter.web.service;

import com.lframework.starter.common.exceptions.impl.ParameterNotFoundException;

public interface SysConfService extends BaseService {

  /**
   * 根据Key查询
   *
   * @param key
   * @return 如果不存在则返回null
   */
  String findByKey(String key);

  /**
   * 根据Key查询
   *
   * @param key
   * @return 如果不存在则抛异常
   */
  String findRequiredByKey(String key) throws ParameterNotFoundException;

  /**
   * 根据Key查询 如果value不存在则返回defaultValue
   *
   * @param key
   * @param defaultValue
   * @return
   */
  String findByKey(String key, String defaultValue);

  /**
   * 根据Key查询Boolean
   *
   * @param key
   * @return
   */
  Boolean getBoolean(String key);

  /**
   * 根据Key查询Boolean
   *
   * @param key
   * @return
   */
  Boolean getBoolean(String key, Boolean defaultValue);

  /**
   * 根据Key查询Integer
   *
   * @param key
   * @return
   */
  Integer getInteger(String key);

  /**
   * 根据Key查询Integer
   *
   * @param key
   * @return
   */
  Integer getInteger(String key, Integer defaultValue);
}
