package com.lframework.starter.web.utils;

import com.lframework.starter.common.utils.StringUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SimpleMap<K, V> extends HashMap<K, V> implements Map<K, V>, Cloneable, Serializable {

  public SimpleMap(int initialCapacity, float loadFactor) {

    super(initialCapacity, loadFactor);
  }

  public SimpleMap(int initialCapacity) {

    super(initialCapacity);
  }

  public SimpleMap() {

  }

  public SimpleMap(Map<? extends K, ? extends V> m) {

    super(m);
  }

  public String getString(K k) {

    return getString(k, null);
  }

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

  public Long getLong(K k) {

    return getLong(k, null);
  }

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

  public String toJsonString() {

    return JsonUtil.toJsonString(this);
  }
}
