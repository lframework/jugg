package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.utils.StringUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单Map工具类
 * 继承HashMap并提供便捷的类型转换方法
 * 包括字符串转换、数值转换、空值过滤等功能
 *
 * @author lframework@163.com
 */
public class SimpleMap<K, V> extends HashMap<K, V> implements Map<K, V>, Cloneable, Serializable {

  /**
   * 构造函数（指定初始容量和负载因子）
   *
   * @param initialCapacity 初始容量
   * @param loadFactor 负载因子
   */
  public SimpleMap(int initialCapacity, float loadFactor) {

    super(initialCapacity, loadFactor);
  }

  /**
   * 构造函数（指定初始容量）
   *
   * @param initialCapacity 初始容量
   */
  public SimpleMap(int initialCapacity) {

    super(initialCapacity);
  }

  /**
   * 默认构造函数
   */
  public SimpleMap() {

  }

  /**
   * 构造函数（基于现有Map）
   *
   * @param m 现有Map，不能为null
   */
  public SimpleMap(Map<? extends K, ? extends V> m) {

    super(m);
  }

  /**
   * 获取字符串值（使用默认值null）
   *
   * @param k 键，不能为null
   * @return 字符串值，如果不存在或转换失败则返回null
   */
  public String getString(K k) {

    return getString(k, null);
  }

  /**
   * 获取字符串值（指定默认值）
   *
   * @param k 键，不能为null
   * @param defaultValue 默认值，可以为null
   * @return 字符串值，如果不存在则返回默认值
   */
  public String getString(K k, V defaultValue) {

    Object v = this.getOrDefault(k, defaultValue);
    if (v == null) {
      return null;
    }
    if (v instanceof String) {
      return (String) v;
    }

    return String.valueOf(v);
  }

  /**
   * 获取长整型值（使用默认值null）
   *
   * @param k 键，不能为null
   * @return 长整型值，如果不存在或转换失败则返回null
   */
  public Long getLong(K k) {

    return getLong(k, null);
  }

  /**
   * 获取长整型值（指定默认值）
   *
   * @param k 键，不能为null
   * @param defaultValue 默认值，可以为null
   * @return 长整型值，如果不存在或转换失败则返回默认值
   */
  public Long getLong(K k, V defaultValue) {

    Object v = this.getOrDefault(k, defaultValue);
    if (v == null || StringUtil.isEmpty(v.toString())) {
      return null;
    }
    if (v instanceof Long) {
      return (Long) v;
    }

    return Long.valueOf(v.toString());
  }

  /**
   * 重写put方法，过滤空值
   * 只有当值不为null且不为空字符串时才存储
   *
   * @param key 键，不能为null
   * @param value 值，如果为null或空字符串则不存储
   * @return 如果存储成功则返回原值，否则返回null
   */
  @Override
  public V put(K key, V value) {

    if (value == null) {
      return null;
    }

    if (value instanceof CharSequence && StringUtil.isEmpty((CharSequence) value)) {
      return null;
    }

    return super.put(key, value);
  }

  /**
   * 转换为JSON字符串
   * 将当前Map转换为JSON格式的字符串
   *
   * @return JSON字符串
   */
  public String toJsonString() {

    return JsonUtil.toJsonString(this);
  }
}
