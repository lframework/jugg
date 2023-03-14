package com.lframework.starter.tenant.interceptors;

import com.lframework.starter.web.components.tenant.TenantInterceptor;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.common.security.SecurityUtil;
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
}
