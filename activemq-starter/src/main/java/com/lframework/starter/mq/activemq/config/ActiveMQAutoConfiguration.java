package com.lframework.starter.mq.activemq.config;

import com.lframework.starter.mq.activemq.producer.ActiveMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class ActiveMQAutoConfiguration {

  @Bean
  public ActiveMQProducer activeMQProducer() {
    return new ActiveMQProducer();
  }
}
