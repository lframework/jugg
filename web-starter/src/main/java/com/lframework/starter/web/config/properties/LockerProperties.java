package com.lframework.starter.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jugg.locker")
public class LockerProperties {

  /**
   * 锁类型，默认使用的JUC的可重入锁
   */
  private String type;
}
