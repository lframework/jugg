package com.lframework.starter.web.threads;

import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.components.tenant.TenantContextHolder;
import com.lframework.starter.web.utils.TenantUtil;
import java.util.concurrent.Callable;

/**
 * 默认Callable 可传递当前登录人信息
 *
 * @author zmj
 * @since 2022/8/19
 */
public class DefaultCallable<T> implements Callable<T> {

  private final AbstractUserDetails user;

  private final Callable<T> callable;

  private final Integer tenantId;

  public DefaultCallable(Callable<T> callable) {
    this.user = SecurityUtil.getCurrentUser();
    this.callable = callable;
    this.tenantId = TenantUtil.enableTenant() ? TenantContextHolder.getTenantId() : null;
  }

  public DefaultCallable(AbstractUserDetails user, Callable<T> callable) {
    this.user = user;
    this.callable = callable;
    this.tenantId = TenantUtil.enableTenant() ? TenantContextHolder.getTenantId() : null;
  }

  public DefaultCallable(AbstractUserDetails user, Callable<T> callable, Integer tenantId) {
    this.user = user;
    this.callable = callable;
    this.tenantId = tenantId;
  }

  @Override
  public T call() throws Exception {
    try {
      if (this.user != null) {
        SecurityUtil.setCurrentUser(this.user);
      }
      if (this.tenantId != null) {
        TenantContextHolder.setTenantId(this.tenantId);
      }
      return this.callable.call();
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
