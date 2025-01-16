package com.lframework.starter.mq.activemq.config;

import com.lframework.starter.mq.activemq.producer.ActiveMqProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class ActiveMqAutoConfiguration {

  @Bean
  public ActiveMqProducer activeMqProducer(JmsTemplate jmsTemplate) {
    return new ActiveMqProducer(jmsTemplate);
  }
}
