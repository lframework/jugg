package com.lframework.starter.cloud.components.trace;

import brave.Tracer;
import com.lframework.starter.web.components.trace.TraceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CloudTraceBuilder implements TraceBuilder {

  @Autowired
  private Tracer tracer;

  @Override
  public String getTraceId() {
    return tracer.currentSpan().context().traceIdString();
  }

  @Override
  public String getTraceId(boolean create) {
    return tracer.currentSpan().context().traceIdString();
  }

  @Override
  public void removeTraceId() {

    // 空实现
  }
}
