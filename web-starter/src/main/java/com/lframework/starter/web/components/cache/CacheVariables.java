package com.lframework.starter.web.components.cache;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.utils.TenantUtil;
import org.springframework.stereotype.Component;

/**
 * 缓存变量
 */
@Component("cacheVariables")
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
