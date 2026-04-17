package com.lframework.starter.web.core.interceptors;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.components.tenant.TenantInterceptor;
import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.inner.service.TenantService;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class TenantInterceptorImpl implements TenantInterceptor {

  private TenantService tenantService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    // 获取租户ID有三种途径
    // 1、读取header中的X-Tenant-Id
    // 2、根据域名获取租户ID
    // 3、根据登录信息获取租户ID
    // 优先级：3 > 2 > 1

    // 从登录信息中获取
    Integer tenantId = SecurityUtil.getCurrentTenantId();
    if (tenantId != null) {
      TenantContextHolder.setTenantId(tenantId);
      return true;
    }

    // 根据域名获取
    String serverName = request.getServerName();
    List<Tenant> tenantList = tenantService.findAll();
    if (CollectionUtil.isNotEmpty(tenantList)) {
      for (Tenant tenant : tenantList) {
        if (serverName.equals(tenant.getServerName())) {
          TenantContextHolder.setTenantId(tenant.getId());
          return true;
        }
      }
    }

    // 读取header中的X-Tenant-Id
    String tenantIdStr = request.getHeader("X-Tenant-Id");
    if (RegUtil.isMatch(PatternPool.PATTERN_IS_INTEGER, tenantIdStr)) {
      Integer targetTenantId = Integer.valueOf(tenantIdStr);
      if (tenantList.stream().anyMatch(t -> t.getId().equals(targetTenantId))) {
        TenantContextHolder.setTenantId(targetTenantId);
        return true;
      }
    }

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    TenantContextHolder.clearTenantId();
  }
}
