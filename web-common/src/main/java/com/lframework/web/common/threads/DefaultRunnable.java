package com.lframework.web.common.threads;

import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;

/**
 * 默认的Runnable 可传递当前登录人信息
 *
 * @author zmj
 * @since 2022/8/19
 */
public class DefaultRunnable implements Runnable {

  private final AbstractUserDetails user;

  private final Runnable runnable;

  public DefaultRunnable(AbstractUserDetails user, Runnable runnable) {
    this.user = user;
    this.runnable = runnable;
  }

  @Override
  public void run() {
    try {
      SecurityUtil.setCurrentUser(this.user);
      this.runnable.run();
    } finally {
      SecurityUtil.removeCurrentUser();
    }
  }
}
