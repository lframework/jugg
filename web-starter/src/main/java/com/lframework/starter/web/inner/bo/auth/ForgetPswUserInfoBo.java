package com.lframework.starter.web.inner.bo.auth;

import com.lframework.starter.web.core.bo.SuperBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ForgetPswUserInfoBo implements SuperBo {

  /**
   * 用户名
   */
  @ApiModelProperty("用户名")
  private String username;

  /**
   * 邮箱
   */
  @ApiModelProperty("邮箱，脱敏")
  private String email;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话，脱敏")
  private String telephone;
}
