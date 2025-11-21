package com.lframework.starter.web.inner.events.system;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class DeleteSysUserGroupEvent extends ApplicationEvent {

  /**
   * 通知组ID
   */
  private String id;

  /**
   * 通知组名称
   */
  private String name;

  public DeleteSysUserGroupEvent(Object source) {
    super(source);
  }
}
