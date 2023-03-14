package com.lframework.starter.mq.producer;

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
   * @param queue  队列名
   * @param data   数据
   * @param millis 毫秒
   */
  void sendDelayMessage(String queue, Serializable data, long millis);

  /**
   * 发送即时消息
   *
   * @param queue 队列名
   * @param data  数据
   */
  void sendMessage(String queue, Serializable data);

  /**
   * 发送延时消息
   *
   * @param queue  队列名
   * @param millis 毫秒
   */
  void sendDelayMessage(String queue, long millis);

  /**
   * 发送即时消息
   *
   * @param queue 队列名
   */
  void sendMessage(String queue);
}
