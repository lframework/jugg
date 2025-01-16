package com.lframework.starter.web.interceptors;

import com.lframework.starter.web.components.tenant.TenantInterceptor;
import com.lframework.starter.web.components.tenant.TenantContextHolder;
import com.lframework.starter.web.components.security.SecurityUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TenantInterceptorImpl implements TenantInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    Integer tenantId = SecurityUtil.getCurrentTenantId();
    if (tenantId != null) {
      TenantContextHolder.setTenantId(tenantId);
      return true;
    }

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    TenantContextHolder.clearTenantId();
  }
}
