package com.lframework.starter.security.event;

import com.lframework.starter.web.components.security.AbstractUserDetails;
import org.springframework.context.ApplicationEvent;

/**
 * 用户退出登录事件
 *
 * @author zmj
 */
public class LogoutEvent extends ApplicationEvent {

  private AbstractUserDetails user;

  public LogoutEvent(Object source, AbstractUserDetails user) {

    super(source);
    this.user = user;
  }

  public AbstractUserDetails getUser() {

    return user;
  }
}
