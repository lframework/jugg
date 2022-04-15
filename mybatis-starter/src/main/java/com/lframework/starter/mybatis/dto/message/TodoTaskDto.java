package com.lframework.starter.mybatis.dto.message;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class TodoTaskDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

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
