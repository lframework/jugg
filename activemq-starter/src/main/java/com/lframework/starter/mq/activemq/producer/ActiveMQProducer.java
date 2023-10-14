package com.lframework.starter.mq.activemq.producer;

import com.lframework.starter.mq.producer.MqProducer;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.TenantUtil;
import java.io.Serializable;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

/**
 * ActiveMQ生产者
 *
 * @author zmj
 * @since 2022/8/25
 */
public class ActiveMQProducer implements MqProducer {

  @Override
  public void sendDelayMessage(String queue, Serializable data, long millis) {

    JmsTemplate template = ApplicationUtil.getBean(JmsTemplate.class);
    template.send(queue, session -> {

      Message msg = session.createObjectMessage(data);
      if (TenantUtil.enableTenant()) {
        msg.setIntProperty("tenantId", TenantContextHolder.getTenantId());
      }
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, millis);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1000);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
      return msg;
    });
  }

  @Override
  public void sendMessage(String queue, Serializable data) {

    JmsMessagingTemplate template = ApplicationUtil.getBean(JmsMessagingTemplate.class);
    template.convertAndSend(queue, data);
  }

  @Override
  public void sendDelayMessage(String queue, long millis) {
    JmsTemplate template = ApplicationUtil.getBean(JmsTemplate.class);
    template.send(queue, session -> {

      Message msg = session.createTextMessage();
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, millis);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1000);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
      return msg;
    });
  }

  @Override
  public void sendMessage(String queue) {
    JmsTemplate template = ApplicationUtil.getBean(JmsTemplate.class);
    template.send(queue, Session::createTextMessage);
  }
}
