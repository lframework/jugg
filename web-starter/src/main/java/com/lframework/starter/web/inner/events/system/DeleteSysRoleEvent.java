package com.lframework.starter.web.inner.events.system;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class DeleteSysRoleEvent extends ApplicationEvent {

  /**
   * 角色ID
   */
  private String id;

  /**
   * 角色名称
   */
  private String name;

  public DeleteSysRoleEvent(Object source) {
    super(source);
  }
}
