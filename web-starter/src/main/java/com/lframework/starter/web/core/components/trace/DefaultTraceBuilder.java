package com.lframework.starter.web.core.components.trace;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.slf4j.MDC;

public class DefaultTraceBuilder implements TraceBuilder {

  private final Tracer tracer;

  public DefaultTraceBuilder() {
    this(null);
  }

  public DefaultTraceBuilder(Tracer tracer) {
    this.tracer = tracer;
  }

  @Override
  public String getTraceId() {

    if (tracer != null) {
      Span span = tracer.currentSpan();
      if (span != null && span.context() != null) {
        return span.context().traceId();
      }
    }

    String traceId = MDC.get("traceId");
    if (traceId != null) {
      return traceId;
    }

    return null;
  }

  @Override
  public String getTraceId(boolean create) {

    return getTraceId();
  }

  @Override
  public void removeTraceId() {

  }
}
