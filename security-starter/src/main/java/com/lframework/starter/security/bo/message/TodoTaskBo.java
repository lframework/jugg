package com.lframework.starter.security.bo.message;

import lombok.Data;

@Data
public class TodoTaskBo {

  /**
   * 标题
   */
  private String title;

  /**
   * 创建时间
   */
  private String createTime;

  /**
   * 跳转Url
   */
  private String jumpUrl;
}
