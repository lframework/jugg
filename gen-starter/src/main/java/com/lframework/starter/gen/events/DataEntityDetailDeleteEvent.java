package com.lframework.starter.gen.events;

import org.springframework.context.ApplicationEvent;

public class DataEntityDetailDeleteEvent extends ApplicationEvent {

  /**
   * 数据实体明细ID
   */
  private String id;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *               associated (never {@code null})
   */
  public DataEntityDetailDeleteEvent(Object source) {

    super(source);
  }

  public String getId() {

    return id;
  }

  public void setId(String id) {

    this.id = id;
  }
}
