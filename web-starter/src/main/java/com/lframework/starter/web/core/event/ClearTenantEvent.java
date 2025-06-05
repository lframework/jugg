package com.lframework.starter.web.core.event;

import org.springframework.context.ApplicationEvent;

public class ClearTenantEvent extends ApplicationEvent {

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *               associated (never {@code null})
   */
  public ClearTenantEvent(Object source) {
    super(source);
  }
}
