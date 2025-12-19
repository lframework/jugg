package com.lframework.starter.web.core.event;

import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 数据变动事件
 *
 * @param <T>
 */
public abstract class DataChangeEvent<T extends BaseEntity> extends ApplicationEvent {

  @Getter
  private T entity;

  @Getter
  private DataChangeType type;

  public DataChangeEvent(Object source, T entity, DataChangeType type) {
    super(source);
    this.entity = entity;
    this.type = type;
  }
}
