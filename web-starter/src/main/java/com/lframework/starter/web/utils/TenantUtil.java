package com.lframework.starter.web.utils;

import com.lframework.starter.web.common.utils.ApplicationUtil;

public class TenantUtil {

  /**
   * 是否启用多租户
   * @return
   */
  public static boolean enableTenant() {
    String enabled = ApplicationUtil.getProperty("tenant.enabled");
    return "true".equalsIgnoreCase(enabled);
  }
}
