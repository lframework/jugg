package com.lframework.starter.web.websocket.events;

import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import org.springframework.context.ApplicationEvent;

/**
 * 用户连接断开事件 区别于LogoutEvent：LogoutEvent用于表示用户退出登录系统后的事件；而连接断开事件表示用户与后台WS断开连接事件
 */
public class UserDisConnectEvent extends ApplicationEvent {

  private String sessionId;

  private AbstractUserDetails user;

  public UserDisConnectEvent(Object source, String sessionId, AbstractUserDetails user) {
    super(source);
    this.sessionId = sessionId;
    this.user = user;
  }

  public String getSessionId() {
    return sessionId;
  }

  public AbstractUserDetails getUser() {
    return user;
  }
}
