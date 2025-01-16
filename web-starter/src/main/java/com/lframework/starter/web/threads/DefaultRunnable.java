package com.lframework.starter.web.threads;

import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.components.tenant.TenantContextHolder;
import com.lframework.starter.web.utils.TenantUtil;

/**
 * 默认的Runnable 可传递当前登录人信息
 *
 * @author zmj
 * @since 2022/8/19
 */
public class DefaultRunnable implements Runnable {

  private final AbstractUserDetails user;

  private final Runnable runnable;

  private final Integer tenantId;

  public DefaultRunnable(Runnable runnable) {
    this.user = SecurityUtil.getCurrentUser();
    this.runnable = runnable;
    this.tenantId = TenantUtil.enableTenant() ? TenantContextHolder.getTenantId() : null;
  }

  public DefaultRunnable(AbstractUserDetails user, Runnable runnable) {
    this.user = user;
    this.runnable = runnable;
    this.tenantId = TenantUtil.enableTenant() ? TenantContextHolder.getTenantId() : null;
  }

  public DefaultRunnable(AbstractUserDetails user, Runnable runnable, Integer tenantId) {
    this.user = user;
    this.runnable = runnable;
    this.tenantId = tenantId;
  }

  @Override
  public void run() {
    try {
      if (this.user != null) {
        SecurityUtil.setCurrentUser(this.user);
      }
      if (this.tenantId != null) {
        TenantContextHolder.setTenantId(this.tenantId);
      }
      this.runnable.run();
    } finally {
      if (this.user != null) {
        SecurityUtil.removeCurrentUser();
      }
      if (this.tenantId != null) {
        TenantContextHolder.clearTenantId();
      }
    }
  }
}
