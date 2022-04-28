package com.lframework.starter.web.utils;

import com.lframework.common.utils.IdUtil;

public class LogUtil {

  private static final ThreadLocal<String> TRACE_ID_THREAD_LOCAL = new ThreadLocal<>();

  public static String getTraceId() {
    if (TRACE_ID_THREAD_LOCAL.get() == null) {
      TRACE_ID_THREAD_LOCAL.set(IdUtil.getId());
    }

    return TRACE_ID_THREAD_LOCAL.get();
  }

  public static String getTraceId(boolean create) {
    if (create) {
      return getTraceId();
    }

    return TRACE_ID_THREAD_LOCAL.get();
  }

  public static void removeTraceId() {
    TRACE_ID_THREAD_LOCAL.remove();
  }
}
