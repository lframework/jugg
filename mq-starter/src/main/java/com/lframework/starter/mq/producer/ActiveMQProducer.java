package com.lframework.starter.mq.producer;

import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import javax.jms.Message;
import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * ActiveMQ生产者
 *
 * @author zmj
 * @since 2022/8/25
 */
@Component
public class ActiveMQProducer implements MqProducer {

  @Override
  public void sendMessage(String destinationName, Object data, long millis) {

    JmsTemplate template = ApplicationUtil.getBean(JmsTemplate.class);
    template.send(destinationName, session -> {

      Message msg = session.createTextMessage(JsonUtil.toJsonString(data));
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, millis);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1000);
      msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
      return msg;
    });
  }

  @Override
  public void sendMessage(String destinationName, Object data) {

    JmsMessagingTemplate template = ApplicationUtil.getBean(JmsMessagingTemplate.class);
    template.convertAndSend(destinationName, JsonUtil.toJsonString(data));
  }
}
