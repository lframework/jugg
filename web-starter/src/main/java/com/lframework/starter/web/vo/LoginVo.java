package com.lframework.starter.web.vo;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录Vo
 *
 * @author zmj
 */
@Data
public class LoginVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 登录名
   */
  @NotBlank(message = "登录名不能为空！")
  private String username;

  /**
   * 密码
   */
  @NotBlank(message = "密码不能为空！")
  private String password;

  /**
   * SN 用于验证码校验
   */
  @NotBlank(message = "SN不能为空！")
  private String sn;

  /**
   * 验证码
   */
  @NotBlank(message = "验证码不能为空！")
  private String captcha;
}
