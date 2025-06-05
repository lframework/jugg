package com.lframework.starter.web.core.components.tenant;

import com.lframework.starter.web.core.event.ClearTenantEvent;
import com.lframework.starter.web.core.event.SetTenantEvent;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.TenantUtil;

public class TenantContextHolder {

  private static final ThreadLocal<Integer> TENANT_ID = new InheritableThreadLocal<>();

  public static void setTenantId(Integer tenantId) {
    if (tenantId == null) {
      throw new RuntimeException("tenantId不能为空！");
    }
    TENANT_ID.set(tenantId);

    SetTenantEvent event = new SetTenantEvent(TenantContextHolder.class, tenantId);
    ApplicationUtil.publishEvent(event);
  }

  public static Integer getTenantId() {
    return TenantUtil.enableTenant() ? TENANT_ID.get() : null;
  }

  public static String getTenantIdStr() {
    Integer tenantId = getTenantId();
    if (tenantId == null) {
      return null;
    } else {
      return String.valueOf(tenantId);
    }
  }

  public static void clearTenantId() {
    TENANT_ID.remove();

    ClearTenantEvent event = new ClearTenantEvent(TenantContextHolder.class);
    ApplicationUtil.publishEvent(event);
  }
}
