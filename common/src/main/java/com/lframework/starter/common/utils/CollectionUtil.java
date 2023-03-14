package com.lframework.starter.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author zmj
 */
public class CollectionUtil extends cn.hutool.core.collection.CollectionUtil {

  public static <T> List<T> emptyList() {
    return new ArrayList<>(0);
  }

  public static <K, V> Map<K, V> emptyMap() {
    return new HashMap<>(0);
  }
}
