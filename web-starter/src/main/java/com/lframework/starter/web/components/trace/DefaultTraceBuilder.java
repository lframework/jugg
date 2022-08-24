package com.lframework.starter.web.components.trace;

import com.lframework.starter.web.utils.IdUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(TraceBuilder.class)
public class DefaultTraceBuilder implements TraceBuilder {

  private static final ThreadLocal<String> TRACE_ID_THREAD_LOCAL = new ThreadLocal<>();

  @Override
  public String getTraceId() {
    if (TRACE_ID_THREAD_LOCAL.get() == null) {
      TRACE_ID_THREAD_LOCAL.set(IdUtil.getUUID());
    }

    return TRACE_ID_THREAD_LOCAL.get();
  }

  @Override
  public String getTraceId(boolean create) {
    if (create) {
      return getTraceId();
    }

    return TRACE_ID_THREAD_LOCAL.get();
  }

  @Override
  public void removeTraceId() {
    TRACE_ID_THREAD_LOCAL.remove();
  }
}
