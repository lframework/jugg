package com.lframework.starter.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jugg.security-upload")
public class SecurityUploadProperties {

  /**
   * 下载时的域名
   */
  private String domain;

  /**
   * 上传文件的路径
   */
  private String location;
}
