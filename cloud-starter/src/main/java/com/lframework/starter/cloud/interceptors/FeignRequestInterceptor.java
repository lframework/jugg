package com.lframework.starter.cloud.interceptors;

import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.utils.RequestUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Enumeration;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate requestTemplate) {
    try {
      HttpServletRequest request = RequestUtil.getRequest();

      Enumeration<String> headerNames = request.getHeaderNames();
      while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        requestTemplate.header(headerName, request.getHeader(headerName));
      }

      // 远程调用传递租户ID
      requestTemplate.header("X-Tenant-Id", TenantContextHolder.getTenantIdStr());
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
    }
  }
}
