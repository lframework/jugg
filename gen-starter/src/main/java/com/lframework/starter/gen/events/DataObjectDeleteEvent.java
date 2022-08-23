package com.lframework.starter.gen.events;

import java.util.List;
import org.springframework.context.ApplicationEvent;

public class DataObjectDeleteEvent extends ApplicationEvent {

  /**
   * 数据对象ID
   */
  private String id;

  private List<String> columnIds;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *               associated (never {@code null})
   */
  public DataObjectDeleteEvent(Object source) {

    super(source);
  }

  public String getId() {

    return id;
  }

  public void setId(String id) {

    this.id = id;
  }

  public List<String> getColumnIds() {

    return columnIds;
  }

  public void setColumnIds(List<String> columnIds) {

    this.columnIds = columnIds;
  }
}
