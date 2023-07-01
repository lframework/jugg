package com.lframework.starter.websocket.config;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.dto.WsPushData;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.websocket.components.WsDataPushWorker;
import com.lframework.starter.websocket.components.WsDataPusher;
import com.lframework.starter.websocket.components.WsDataPusherImpl;
import com.lframework.starter.websocket.handler.WsHandler;
import com.lframework.starter.websocket.interceptor.WsHandshakeInterceptor;
import com.lframework.starter.websocket.listener.WsUserConnectListener;
import com.lframework.starter.websocket.listener.WsUserDisConnectListener;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableConfigurationProperties(WsProperties.class)
@ConditionalOnProperty(prefix = "jugg.ws", value = "enabled", havingValue = "true")
public class WsAutoConfiguration implements WebSocketConfigurer {

  @Autowired
  private WsProperties properties;

  @Bean
  public WsDataPusher wsDataPusher(RedisTemplate redisTemplate) {
    return new WsDataPusherImpl(redisTemplate, properties);
  }

  @Bean
  public RedisMessageListenerContainer redisMessageListenerContainer(
      RedisConnectionFactory redisConnectionFactory) {
    RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
    redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
    redisMessageListenerContainer.addMessageListener((message, pattern) -> {
      WsDataPushWorker.push(
          JsonUtil.parseObject(StringUtil.str(message.getBody(), StandardCharsets.UTF_8),
              WsPushData.class));
    }, ChannelTopic.of(properties.getTopic()));
    return redisMessageListenerContainer;
  }

  @Bean
  public WsHandler wsHandler() {
    return new WsHandler();
  }

  @Bean
  public WsHandshakeInterceptor wsHandshakeInterceptor() {
    return new WsHandshakeInterceptor();
  }

  @Bean
  public WsUserConnectListener wsUserConnectListener() {
    return new WsUserConnectListener();
  }

  @Bean
  public WsUserDisConnectListener wsUserDisConnectListener() {
    return new WsUserDisConnectListener();
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    WebSocketHandlerRegistration registration = registry.addHandler(wsHandler(), "/message/bus")
        .addInterceptors(wsHandshakeInterceptor());
    if (properties.isSupportCrossDomain()) {
      registration.setAllowedOrigins("*");
    }
  }
}
