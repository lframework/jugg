package com.lframework.starter.mq.core.producer;

import com.lframework.starter.mq.core.queue.QueueDefinition;
import java.io.Serializable;

/**
 * MQ生产者
 *
 * @author zmj
 * @since 2022/8/25
 */
public interface MqProducer {

  /**
   * 发送延时消息
   *
   * @param definition 队列
   * @param data       数据
   * @param millis     毫秒
   */
  void sendDelayMessage(QueueDefinition definition, Serializable data, long millis);

  /**
   * 发送即时消息
   *
   * @param definition 队列
   * @param data       数据
   */
  void sendMessage(QueueDefinition definition, Serializable data);

  /**
   * 发送延时消息
   *
   * @param definition 队列
   * @param millis     毫秒
   */
  void sendDelayMessage(QueueDefinition definition, long millis);

  /**
   * 发送即时消息
   *
   * @param definition 队列
   */
  void sendMessage(QueueDefinition definition);

  /**
   * 发送延时消息
   *
   * @param definition 队列
   * @param data       数据
   * @param millis     毫秒
   * @param tenantId   租户ID
   */
  void sendDelayMessage(QueueDefinition definition, Serializable data, long millis,
      Integer tenantId);

  /**
   * 发送即时消息
   *
   * @param definition 队列
   * @param data       数据
   * @param tenantId   租户ID
   */
  void sendMessage(QueueDefinition definition, Serializable data, Integer tenantId);

  /**
   * 发送延时消息
   *
   * @param definition 队列
   * @param millis     毫秒
   * @param tenantId   租户ID
   */
  void sendDelayMessage(QueueDefinition definition, long millis, Integer tenantId);

  /**
   * 发送即时消息
   *
   * @param definition 队列
   * @param tenantId   租户ID
   */
  void sendMessage(QueueDefinition definition, Integer tenantId);
}
