package com.lframework.starter.web.common.tenant;

import com.lframework.starter.web.common.event.ClearTenantEvent;
import com.lframework.starter.web.common.event.SetTenantEvent;
import com.lframework.starter.web.common.utils.ApplicationUtil;

public class TenantContextHolder {

  private static final ThreadLocal<Integer> TENANT_ID = new ThreadLocal<>();

  public static void setTenantId(Integer tenantId) {
    if (tenantId == null) {
      throw new RuntimeException("tenantId不能为空！");
    }
    TENANT_ID.set(tenantId);

    SetTenantEvent event = new SetTenantEvent(TenantContextHolder.class, tenantId);
    ApplicationUtil.publishEvent(event);
  }

  public static Integer getTenantId() {
    return TENANT_ID.get();
  }

  public static void clearTenantId() {
    TENANT_ID.remove();

    ClearTenantEvent event = new ClearTenantEvent(TenantContextHolder.class);
    ApplicationUtil.publishEvent(event);
  }
}
