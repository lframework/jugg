package com.lframework.starter.mq.activemq.producer;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.mq.activemq.queue.ActiveMqQueueDefinition;
import com.lframework.starter.mq.core.producer.MqProducer;
import com.lframework.starter.mq.core.queue.QueueDefinition;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.utils.TenantUtil;
import java.io.Serializable;
import javax.jms.Message;
import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsTemplate;

/**
 * ActiveMQ生产者
 *
 * @author zmj
 * @since 2022/8/25
 */
public class ActiveMqProducer implements MqProducer {

  private final JmsTemplate jmsTemplate;

  public ActiveMqProducer(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
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

    jmsTemplate.send(convertDefinition(definition), session -> {

      Message msg = session.createObjectMessage(data);
      if (TenantUtil.enableTenant()) {
        Assert.notNull(tenantId);
        msg.setIntProperty("tenantId", tenantId);
      }
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, millis);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1000);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
      return msg;
    });
  }

  @Override
  public void sendMessage(QueueDefinition definition, Serializable data, Integer tenantId) {

    jmsTemplate.send(convertDefinition(definition), session -> {

      Message msg = session.createObjectMessage(data);
      if (TenantUtil.enableTenant()) {
        Assert.notNull(tenantId);
        msg.setIntProperty("tenantId", tenantId);
      }
      return msg;
    });
  }

  @Override
  public void sendDelayMessage(QueueDefinition definition, long millis, Integer tenantId) {
    Assert.isTrue(millis <= Integer.MAX_VALUE && millis >= 0);

    jmsTemplate.send(convertDefinition(definition), session -> {

      Message msg = session.createTextMessage();
      if (TenantUtil.enableTenant()) {
        Assert.notNull(tenantId);
        msg.setIntProperty("tenantId", tenantId);
      }
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, millis);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1000);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
      return msg;
    });
  }

  @Override
  public void sendMessage(QueueDefinition definition, Integer tenantId) {
    jmsTemplate.send(convertDefinition(definition), session -> {

      Message msg = session.createTextMessage();
      if (TenantUtil.enableTenant()) {
        Assert.notNull(tenantId);
        msg.setIntProperty("tenantId", tenantId);
      }
      return msg;
    });
  }

  private String convertDefinition(QueueDefinition definition) {
    if (definition == null) {
      throw new DefaultSysException("definition不能为空！");
    }

    if (definition instanceof ActiveMqQueueDefinition) {
      return ((ActiveMqQueueDefinition) definition).getQueue();
    } else {
      throw new DefaultSysException(
          "没有找到" + definition.getClass().getName() + "对应的QueueDefinition");
    }
  }
}
