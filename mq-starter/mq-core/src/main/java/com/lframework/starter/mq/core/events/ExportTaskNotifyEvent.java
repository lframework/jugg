package com.lframework.starter.mq.core.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class ExportTaskNotifyEvent extends ApplicationEvent {

  @Getter
  private String createById;

  public ExportTaskNotifyEvent(Object source, String createById) {
    super(source);
    this.createById = createById;
  }
}
