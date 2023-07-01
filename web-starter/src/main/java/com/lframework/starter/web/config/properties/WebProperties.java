package com.lframework.starter.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jugg.web")
public class WebProperties {

  /**
   * 不需要认证的Url
   */
  private String filterUrl;

  /**
   * 终端ID
   */
  private Long workerId = -1L;

  /**
   * 数据中心ID
   */
  private Long centerId = -1L;
}
