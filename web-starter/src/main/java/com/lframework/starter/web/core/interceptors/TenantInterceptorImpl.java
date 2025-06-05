package com.lframework.starter.web.core.interceptors;

import com.lframework.starter.web.core.components.tenant.TenantInterceptor;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.components.security.SecurityUtil;
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
