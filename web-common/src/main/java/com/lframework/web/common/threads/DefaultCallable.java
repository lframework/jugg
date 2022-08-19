package com.lframework.web.common.threads;

import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;
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

  public DefaultCallable(AbstractUserDetails user, Callable<T> callable) {
    this.user = user;
    this.callable = callable;
  }

  @Override
  public T call() throws Exception {
    try {
      SecurityUtil.setCurrentUser(this.user);
      return this.callable.call();
    } finally {
      SecurityUtil.removeCurrentUser();
    }
  }
}
