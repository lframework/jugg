package com.lframework.starter.web.inner.events.system;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class DeleteSysDeptEvent extends ApplicationEvent {

  /**
   * 部门ID
   */
  private String id;

  /**
   * 部门名称
   */
  private String name;

  public DeleteSysDeptEvent(Object source) {
    super(source);
  }
}
