package com.lframework.starter.web.components.trace;

import org.slf4j.MDC;

public class DefaultTraceBuilder implements TraceBuilder {

  @Override
  public String getTraceId() {

    return MDC.get("X-B3-TraceId");
  }

  @Override
  public String getTraceId(boolean create) {

    return getTraceId();
  }

  @Override
  public void removeTraceId() {

  }
}
