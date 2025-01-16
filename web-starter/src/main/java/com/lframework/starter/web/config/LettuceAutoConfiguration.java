package com.lframework.starter.web.config;

import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.NettyCustomizer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public class LettuceAutoConfiguration {

  @Value("${spring.redis.lettuce.reader-idle-time-seconds:30}")
  private Integer readerIdleTimeSeconds;

  // @Bean
  public LettuceInitializingBean lettuceInitializingBean(RedisConnectionFactory redisConnectionFactory) {
    return new LettuceInitializingBean(redisConnectionFactory);
  }

  @Bean
  public ClientResources clientResources() {
    NettyCustomizer nettyCustomizer = new NettyCustomizer() {
      @Override
      public void afterChannelInitialized(Channel channel) {
        channel.pipeline().addLast(
            //第一个参数readerIdleTimeSeconds设置为小于超时时间timeout，单位为秒，
            //每隔readerIdleTimeSeconds会进行重连,在超时之前重连就能避免命令超时报错。
            new IdleStateHandler(readerIdleTimeSeconds, 0, 0));
        channel.pipeline().addLast(new ChannelDuplexHandler() {
          @Override
          public void userEventTriggered(ChannelHandlerContext ctx, Object isEvt) throws Exception {
            if (isEvt instanceof IdleStateEvent) {
              ctx.disconnect();
            }
          }
        });
      }
    };
    return ClientResources.builder().nettyCustomizer(nettyCustomizer).build();
  }

  @Bean
  public CustomLettuceClientConfigurationBuilderCustomizer customLettuceClientConfigurationBuilderCustomizer() {
    return new CustomLettuceClientConfigurationBuilderCustomizer(clientResources());
  }

  public static class CustomLettuceClientConfigurationBuilderCustomizer implements
      LettuceClientConfigurationBuilderCustomizer {

    private ClientResources clientResources;

    public CustomLettuceClientConfigurationBuilderCustomizer(ClientResources clientResources) {
      this.clientResources = clientResources;
    }

    @Override
    public void customize(LettuceClientConfigurationBuilder clientConfigurationBuilder) {
      clientConfigurationBuilder.clientResources(clientResources);
    }
  }

  public static class LettuceInitializingBean implements InitializingBean {

    private RedisConnectionFactory redisConnectionFactory;

    public LettuceInitializingBean(RedisConnectionFactory redisConnectionFactory) {
      this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
      if (redisConnectionFactory instanceof LettuceConnectionFactory) {
        LettuceConnectionFactory c = (LettuceConnectionFactory) redisConnectionFactory;
        c.setValidateConnection(true);
      }
    }
  }
}
