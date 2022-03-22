package com.lframework.starter.web.utils;

import com.lframework.common.utils.ArrayUtil;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import java.util.Enumeration;
import java.util.Map;

public class ValidateUtil {

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
