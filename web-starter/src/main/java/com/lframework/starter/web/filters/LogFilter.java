package com.lframework.starter.web.filters;

import com.lframework.starter.web.utils.LogUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter
public class LogFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    MDC.put("traceId", LogUtil.getTraceId());

    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      LogUtil.removeTraceId();
    }
  }
}
