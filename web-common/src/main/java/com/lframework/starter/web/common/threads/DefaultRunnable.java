package com.lframework.starter.web.common.threads;

import com.lframework.starter.web.common.security.AbstractUserDetails;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.common.tenant.TenantContextHolder;

/**
 * 默认的Runnable 可传递当前登录人信息
 *
 * @author zmj
 * @since 2022/8/19
 */
public class DefaultRunnable implements Runnable {

  private final AbstractUserDetails user;

  private final Runnable runnable;

  public DefaultRunnable(Runnable runnable) {
    this.user = SecurityUtil.getCurrentUser();
    this.runnable = runnable;
  }

  public DefaultRunnable(AbstractUserDetails user, Runnable runnable) {
    this.user = user;
    this.runnable = runnable;
  }

  @Override
  public void run() {
    try {
      SecurityUtil.setCurrentUser(this.user);
      if (this.user.getTenantId() != null) {
        TenantContextHolder.setTenantId(this.user.getTenantId());
      }
      this.runnable.run();
    } finally {
      SecurityUtil.removeCurrentUser();
      TenantContextHolder.clearTenantId();
    }
  }
}
