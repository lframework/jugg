package com.lframework.starter.web.utils;

import com.lframework.starter.web.config.properties.TenantProperties;

public class TenantUtil {

  /**
   * 是否启用多租户
   * @return
   */
  public static boolean enableTenant() {
    TenantProperties properties = ApplicationUtil.getBean(TenantProperties.class);
    return properties.getEnabled();
  }
}
