package com.lframework.starter.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jugg.upload")
public class UploadProperties {

  /**
   * 下载时的域名
   */
  private String domain;

  /**
   * 载时的baseUrl，与domain的区别：会在url后面拼接/**作为静态资源的url，而domain是后端系统的具体域名，下载时的完整url例如：upload.domain + upload.url/xxx.jpg
   */
  private String url;

  /**
   * 上传文件的路径
   */
  private String location;
}
