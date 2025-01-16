package com.lframework.starter.web.event;

import org.springframework.context.ApplicationEvent;

public class SetTenantEvent extends ApplicationEvent {

  /**
   * 租户ID
   */
  private Integer tenantId;

  public SetTenantEvent(Object source, Integer tenantId) {
    super(source);
    this.tenantId = tenantId;
  }

  public Integer getTenantId() {
    return tenantId;
  }
}
