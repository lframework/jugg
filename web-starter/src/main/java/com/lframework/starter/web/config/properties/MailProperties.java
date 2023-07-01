package com.lframework.starter.web.config.properties;

import lombok.Data;

/**
 * 邮箱配置
 */
@Data
public class MailProperties {

  /**
   * 邮件服务器的SMTP地址
   */
  private String host;

  /**
   * 邮件服务器的SMTP端口
   */
  private Integer port = 25;

  /**
   * 发件人
   */
  private String from;

  /**
   * 用户名
   */
  private String user;

  /**
   * 密码
   */
  private String pass;

  /**
   * 使用SSL安全连接
   */
  private Boolean sslEnable;

  /**
   * SMTP超时时间 默认30s
   */
  private Long timeOut = 30000L;

  /**
   * 连接超时时间 默认1s
   */
  private Long connectTimeOut = 1000L;
}
