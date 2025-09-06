package com.lframework.starter.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 * 基于HuTool的CollectionUtil进行扩展，提供集合操作相关的工具方法
 * 包括集合判空、创建空集合、集合转换等功能
 *
 * @author lframework@163.com
 */
public class CollectionUtil extends cn.hutool.core.collection.CollectionUtil {

  /**
   * 创建空的List集合
   * 返回一个容量为0的ArrayList实例
   *
   * @param <T> 集合元素类型
   * @return 空的List集合
   */
  public static <T> List<T> emptyList() {
    return new ArrayList<>(0);
  }

  /**
   * 创建空的Map集合
   * 返回一个容量为0的HashMap实例
   *
   * @param <K> Map键类型
   * @param <V> Map值类型
   * @return 空的Map集合
   */
  public static <K, V> Map<K, V> emptyMap() {
    return new HashMap<>(0);
  }
}
