package com.lframework.starter.web.core.event;

import com.lframework.starter.common.utils.ReflectUtil;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.starter.web.core.utils.ApplicationUtil;

public class DataChangeEventBuilder {

  public static <T extends BaseEntity, R extends DataChangeEvent<T>> void publishCreate(
      Object source,
      Class<R> clazz, T entity) {
    DataChangeEvent<T> event = newInstance(source, clazz, entity, DataChangeType.CREATE);
    ApplicationUtil.publishEvent(event);
  }

  public static <T extends BaseEntity, R extends DataChangeEvent<T>> R create(
      Object source,
      Class<R> clazz, T entity) {
    return newInstance(source, clazz, entity, DataChangeType.CREATE);
  }

  public static <T extends BaseEntity, R extends DataChangeEvent<T>> void publishUpdate(
      Object source,
      Class<R> clazz, T entity) {
    DataChangeEvent<T> event = newInstance(source, clazz, entity, DataChangeType.UPDATE);
    ApplicationUtil.publishEvent(event);
  }

  public static <T extends BaseEntity, R extends DataChangeEvent<T>> R update(
      Object source,
      Class<R> clazz, T entity) {
    return newInstance(source, clazz, entity, DataChangeType.UPDATE);
  }

  public static <T extends BaseEntity, R extends DataChangeEvent<T>> void publishLogicDelete(
      Object source,
      Class<R> clazz, T entity) {
    DataChangeEvent<T> event = newInstance(source, clazz, entity, DataChangeType.LOGIC_DELETE);
    ApplicationUtil.publishEvent(event);
  }

  public static <T extends BaseEntity, R extends DataChangeEvent<T>> R logicDelete(
      Object source,
      Class<R> clazz, T entity) {
    return newInstance(source, clazz, entity, DataChangeType.LOGIC_DELETE);
  }

  public static <T extends BaseEntity, R extends DataChangeEvent<T>> void publishDelete(
      Object source,
      Class<R> clazz, T entity) {
    DataChangeEvent<T> event = newInstance(source, clazz, entity, DataChangeType.DELETE);
    ApplicationUtil.publishEvent(event);
  }

  public static <T extends BaseEntity, R extends DataChangeEvent<T>> R delete(
      Object source,
      Class<R> clazz, T entity) {
    return newInstance(source, clazz, entity, DataChangeType.DELETE);
  }

  private static <T extends BaseEntity, R extends DataChangeEvent<T>> R newInstance(
      Object source,
      Class<R> clazz, T entity, DataChangeType type) {
    return ReflectUtil.newInstance(clazz, source, entity, type);
  }
}