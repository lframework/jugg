package com.lframework.starter.mq.rabbitmq.queue;

import com.lframework.starter.mq.core.queue.QueueDefinition;
import lombok.Getter;

@Getter
public class RabbitMQQueueDefinition implements QueueDefinition {

  /**
   * 交换机名
   */
  private final String exchange;

  /**
   * 路由键
   */
  private String routingKey;

  public RabbitMQQueueDefinition(String exchange) {
    this.exchange = exchange;
  }

  public RabbitMQQueueDefinition(String exchange, String routingKey) {
    this.exchange = exchange;
    this.routingKey = routingKey;
  }
}
