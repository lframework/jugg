package com.lframework.starter.web.core.components.trace;

public interface TraceBuilder {

  /**
   * 获取TraceId
   *
   * @return
   */
  String getTraceId();

  /**
   * 获取TraceId
   *
   * @param create
   * @return
   */
  String getTraceId(boolean create);

  /**
   * 清空TraceId
   */
  void removeTraceId();
}
