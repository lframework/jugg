package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import java.util.Enumeration;
import java.util.Map;

/**
 * 验证工具类
 * 提供各种类型对象的空值验证功能，支持多种数据类型的判空
 * 包括字符串、集合、数组、Map等类型的空值检查
 *
 * @author lframework@163.com
 */
public class ValidateUtil {

  /**
   * 判断对象是否为空
   * 支持多种类型的空值检查，包括字符串、集合、数组、Map等
   *
   * @param val 要检查的对象，可以为null
   * @return true-对象为空，false-对象不为空
   */
  public static boolean isEmpty(Object val) {

    if (val == null) {
      return true;
    }

    if (val instanceof CharSequence) {
      return StringUtil.isEmpty((CharSequence) val);
    }

    if (val instanceof Iterable) {
      return CollectionUtil.isEmpty((Iterable<?>) val);
    }

    if (val instanceof Map) {
      return CollectionUtil.isEmpty((Map<?, ?>) val);
    }

    if (val instanceof Enumeration) {
      return CollectionUtil.isEmpty((Enumeration<?>) val);
    }

    if (val instanceof Object[]) {
      return ArrayUtil.isEmpty((Object[]) val);
    }

    return false;
  }
}
