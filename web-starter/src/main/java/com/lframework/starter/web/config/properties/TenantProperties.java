package com.lframework.starter.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jugg.tenant")
public class TenantProperties {

  /**
   * 开启多租户
   */
  private Boolean enabled = Boolean.TRUE;

}
