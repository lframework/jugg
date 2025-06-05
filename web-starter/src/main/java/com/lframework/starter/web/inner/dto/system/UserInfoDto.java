package com.lframework.starter.web.inner.dto.system;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户信息 Dto
 *
 * @author zmj
 */
@Data
public class UserInfoDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "UserInfoDto";

  /**
   * ID
   */
  private String id;

  /**
   * 登录名
   */
  private String username;

  /**
   * 编号
   */
  private String code;

  /**
   * 姓名
   */
  private String name;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 联系电话
   */
  private String telephone;

  /**
   * 性别
   */
  private Integer gender;
}
