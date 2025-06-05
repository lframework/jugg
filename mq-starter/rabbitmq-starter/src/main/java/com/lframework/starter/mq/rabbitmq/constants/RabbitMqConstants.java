package com.lframework.starter.mq.rabbitmq.constants;

import com.lframework.starter.mq.core.queue.QueueDefinition;
import com.lframework.starter.mq.rabbitmq.queue.RabbitMQQueueDefinition;

public class RabbitMqConstants {

  /**
   * 消息通知
   */
  public static final QueueDefinition SYS_NOTIFY = new RabbitMQQueueDefinition(
      RabbitMqStringPool.SYS_NOTIFY_EXCHANGE, RabbitMqStringPool.SYS_NOTIFY_ROUTING_KEY);

  /**
   * 邮件消息
   */
  public static final QueueDefinition SYS_MAIL_MESSAGE = new RabbitMQQueueDefinition(
      RabbitMqStringPool.SYS_MAIL_MESSAGE_EXCHANGE,
      RabbitMqStringPool.SYS_MAIL_MESSAGE_ROUTING_KEY);

  /**
   * 站内信
   */
  public static final QueueDefinition SYS_SITE_MESSAGE = new RabbitMQQueueDefinition(
      RabbitMqStringPool.SYS_SITE_MESSAGE_EXCHANGE,
      RabbitMqStringPool.SYS_SITE_MESSAGE_ROUTING_KEY);

  /**
   * 新增导出任务
   */
  public static final QueueDefinition ADD_EXPORT_TASK = new RabbitMQQueueDefinition(
      RabbitMqStringPool.ADD_EXPORT_TASK_EXCHANGE, RabbitMqStringPool.ADD_EXPORT_TASK_ROUTING_KEY);

  /**
   * 执行导出任务
   */
  public static final QueueDefinition EXECUTE_EXPORT_TASK = new RabbitMQQueueDefinition(
      RabbitMqStringPool.EXECUTE_EXPORT_TASK_EXCHANGE,
      RabbitMqStringPool.EXECUTE_EXPORT_TASK_ROUTING_KEY);
}
