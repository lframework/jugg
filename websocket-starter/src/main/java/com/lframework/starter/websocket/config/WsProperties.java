package com.lframework.starter.websocket.config;

import com.lframework.starter.web.utils.IdUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jugg.ws")
public class WsProperties {

  /**
   * 是否开启WebSocket
   */
  private boolean enabled = Boolean.FALSE;

  /**
   * 集群发送消息时的topic
   */
  private String topic = "jugg:ws:topic";

  /**
   * 是否支持跨域
   */
  private boolean supportCrossDomain = true;
}
