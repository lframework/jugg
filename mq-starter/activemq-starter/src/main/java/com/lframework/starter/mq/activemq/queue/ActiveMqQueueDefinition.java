package com.lframework.starter.mq.activemq.queue;

import com.lframework.starter.mq.core.queue.QueueDefinition;
import lombok.Getter;

@Getter
public class ActiveMqQueueDefinition implements QueueDefinition {

  /**
   * 队列名
   */
  private String queue;

  public ActiveMqQueueDefinition(String queue) {
    this.queue = queue;
  }
}
