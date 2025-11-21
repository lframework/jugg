package com.lframework.starter.web.inner.events.system;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class DeleteSysUserEvent extends ApplicationEvent {

  /**
   * 用户ID
   */
  private String id;

  /**
   * 用户姓名
   */
  private String name;

  public DeleteSysUserEvent(Object source) {
    super(source);
  }
}
