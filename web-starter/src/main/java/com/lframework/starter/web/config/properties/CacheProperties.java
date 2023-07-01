package com.lframework.starter.web.config.properties;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jugg.cache")
public class CacheProperties {

  /**
   * 公共缓存过期时间
   */
  private Long ttl = 1800L;

  /**
   * 特殊指定缓存过期时间
   */
  private Map<String, Long> regions;
}
