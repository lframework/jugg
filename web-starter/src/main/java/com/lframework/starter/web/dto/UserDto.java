package com.lframework.starter.web.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户Dto
 *
 * @author zmj
 */
@Data
public class UserDto implements Serializable {

  public static final String CACHE_NAME = "UserDto";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 姓名
   */
  private String name;
}
