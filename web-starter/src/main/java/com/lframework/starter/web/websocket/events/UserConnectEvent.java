package com.lframework.starter.web.websocket.events;

import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import org.springframework.context.ApplicationEvent;

/**
 * 用户连接事件 区别于LoginEvent：LoginEvent用于表示用户登录系统后的事件；而连接事件表示用户与后台WS连接成功事件
 */
public class UserConnectEvent extends ApplicationEvent {

  /**
   * SessionId
   */
  private String sessionId;

  private AbstractUserDetails user;

  public UserConnectEvent(Object source, String sessionId, AbstractUserDetails user) {
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
