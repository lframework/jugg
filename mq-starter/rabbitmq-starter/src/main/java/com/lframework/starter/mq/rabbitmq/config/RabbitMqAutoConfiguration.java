package com.lframework.starter.mq.rabbitmq.config;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.mq.rabbitmq.impl.RabbitMqProducerServiceImpl;
import com.lframework.starter.mq.rabbitmq.listeners.app.ExportTaskNotifyListener;
import com.lframework.starter.mq.rabbitmq.listeners.mq.ExportTaskListener;
import com.lframework.starter.mq.rabbitmq.listeners.mq.SysMailMessageListener;
import com.lframework.starter.mq.rabbitmq.listeners.mq.SysNotifyListener;
import com.lframework.starter.mq.rabbitmq.listeners.mq.SysSiteMessageListener;
import com.lframework.starter.mq.rabbitmq.producer.RabbitMqProducer;
import org.aopalliance.aop.Advice;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.DirectRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableRabbit
@Configuration
@Import({
    RabbitMqProducerServiceImpl.class,
    SysMailMessageListener.class,
    SysNotifyListener.class,
    SysSiteMessageListener.class,
    ExportTaskNotifyListener.class,
    ExportTaskListener.class,
})
public class RabbitMqAutoConfiguration {

  private final ObjectProvider<MessageConverter> messageConverter;

  private final ObjectProvider<MessageRecoverer> messageRecoverer;

  private final ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers;

  private final RabbitProperties properties;

  RabbitMqAutoConfiguration(ObjectProvider<MessageConverter> messageConverter,
      ObjectProvider<MessageRecoverer> messageRecoverer,
      ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers,
      RabbitProperties properties) {
    this.messageConverter = messageConverter;
    this.messageRecoverer = messageRecoverer;
    this.retryTemplateCustomizers = retryTemplateCustomizers;
    this.properties = properties;
  }

  @Bean
  public RabbitMqProducer activeMQProducer(RabbitTemplate rabbitTemplate) {
    return new RabbitMqProducer(rabbitTemplate);
  }

  @Bean(name = "rabbitListenerContainerFactory")
  @ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = "type", havingValue = "simple",
      matchIfMissing = true)
  SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
      SimpleRabbitListenerContainerFactoryConfigurer configurer,
      ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    factory.setAdviceChain(combineAdvice(factory.getAdviceChain()));
    return factory;
  }

  @Bean(name = "rabbitListenerContainerFactory")
  @ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = "type", havingValue = "direct")
  DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory(
      DirectRabbitListenerContainerFactoryConfigurer configurer,
      ConnectionFactory connectionFactory) {
    DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    factory.setAdviceChain(combineAdvice(factory.getAdviceChain()));
    return factory;
  }

  private Advice[] combineAdvice(Advice[] advices) {
    Advice[] result = new Advice[(ArrayUtil.isEmpty(advices) ? 0 : advices.length) + 1];
    if (!ArrayUtil.isEmpty(advices)) {
      for (int i = 0; i < advices.length; i++) {
        result[i] = advices[i];
      }
    }

    result[result.length - 1] = new TenantMethodInterceptor();
    return result;
  }
}
