package com.lframework.starter.web.core.components.cache;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.utils.TenantUtil;

/**
 * 缓存变量
 */
public class CacheVariables {

  /**
   * 租户ID
   *
   * @return
   */
  public static String tenantId() {
    if (!TenantUtil.enableTenant()) {
      return StringPool.EMPTY_STR;
    }
    Integer tenantId = TenantContextHolder.getTenantId();
    if (tenantId == null) {
      return StringPool.EMPTY_STR;
    }

    return String.valueOf(tenantId) + "::";
  }
}
