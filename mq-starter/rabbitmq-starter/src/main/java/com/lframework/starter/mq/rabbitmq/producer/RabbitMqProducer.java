package com.lframework.starter.mq.rabbitmq.producer;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.mq.core.producer.MqProducer;
import com.lframework.starter.mq.core.queue.QueueDefinition;
import com.lframework.starter.mq.rabbitmq.queue.RabbitMQQueueDefinition;
import com.lframework.starter.web.components.tenant.TenantContextHolder;
import com.lframework.starter.web.utils.TenantUtil;
import java.io.Serializable;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * RabbitMQ生产者
 *
 * @author zmj
 * @since 2022/8/25
 */
public class RabbitMqProducer implements MqProducer {

  private final RabbitTemplate rabbitTemplate;

  private final Object EMPTY_MSG = new Object();

  public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void sendDelayMessage(QueueDefinition definition, Serializable data, long millis) {

    sendDelayMessage(definition, data, millis, TenantContextHolder.getTenantId());
  }

  @Override
  public void sendMessage(QueueDefinition definition, Serializable data) {

    sendMessage(definition, data, TenantContextHolder.getTenantId());
  }

  @Override
  public void sendDelayMessage(QueueDefinition definition, long millis) {
    sendDelayMessage(definition, millis, TenantContextHolder.getTenantId());
  }

  @Override
  public void sendMessage(QueueDefinition definition) {
    sendMessage(definition, TenantContextHolder.getTenantId());
  }

  @Override
  public void sendDelayMessage(QueueDefinition definition, Serializable data, long millis,
      Integer tenantId) {
    Assert.isTrue(millis <= Integer.MAX_VALUE && millis >= 0);
    rabbitTemplate.convertAndSend(
        convertExchange(definition),
        convertRoutingKey(definition),
        data,
        m -> {
          m.getMessageProperties().setDelay((int) millis);
          if (TenantUtil.enableTenant() && tenantId != null) {
            m.getMessageProperties().setHeader("tenantId", tenantId);
          }

          return m;
        }
    );
  }

  @Override
  public void sendMessage(QueueDefinition definition, Serializable data, Integer tenantId) {
    rabbitTemplate.convertAndSend(convertExchange(definition), convertRoutingKey(definition), data,
        m -> {
          if (TenantUtil.enableTenant() && tenantId != null) {
            m.getMessageProperties().setHeader("tenantId", tenantId);
          }

          return m;
        });
  }

  @Override
  public void sendDelayMessage(QueueDefinition definition, long millis, Integer tenantId) {
    Assert.isTrue(millis <= Integer.MAX_VALUE && millis >= 0);
    rabbitTemplate.convertAndSend(
        convertExchange(definition),
        convertRoutingKey(definition),
        EMPTY_MSG,
        m -> {
          m.getMessageProperties().setDelay((int) millis);

          if (TenantUtil.enableTenant() && tenantId != null) {
            m.getMessageProperties().setHeader("tenantId", tenantId);
          }

          return m;
        }
    );
  }

  @Override
  public void sendMessage(QueueDefinition definition, Integer tenantId) {
    rabbitTemplate.convertAndSend(convertExchange(definition), convertRoutingKey(definition),
        EMPTY_MSG, m -> {
          if (TenantUtil.enableTenant() && tenantId != null) {
            m.getMessageProperties().setHeader("tenantId", tenantId);
          }

          return m;
        });
  }

  private String convertExchange(QueueDefinition definition) {
    if (definition == null) {
      throw new DefaultSysException("definition不能为空！");
    }

    if (definition instanceof RabbitMQQueueDefinition) {
      return ((RabbitMQQueueDefinition) definition).getExchange();
    } else {
      throw new DefaultSysException(
          "没有找到" + definition.getClass().getName() + "对应的QueueDefinition");
    }
  }

  private String convertRoutingKey(QueueDefinition definition) {
    if (definition == null) {
      throw new DefaultSysException("definition不能为空！");
    }

    if (definition instanceof RabbitMQQueueDefinition) {
      return ((RabbitMQQueueDefinition) definition).getRoutingKey();
    } else {
      throw new DefaultSysException(
          "没有找到" + definition.getClass().getName() + "对应的QueueDefinition");
    }
  }
}
