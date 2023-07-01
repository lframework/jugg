package com.lframework.starter.web.filters;

import com.lframework.starter.web.components.trace.TraceBuilder;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

public class LogFilter implements Filter {

  @Autowired
  private TraceBuilder traceBuilder;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    MDC.put("traceId", traceBuilder.getTraceId());

    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      traceBuilder.removeTraceId();
    }
  }
}
