package com.lframework.starter.web.inner.bo.auth;

import com.lframework.starter.web.core.bo.SuperBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ForgetPswUserInfoBo implements SuperBo {

  /**
   * 用户名
   */
  @Schema(description = "用户名")
  private String username;

  /**
   * 邮箱
   */
  @Schema(description = "邮箱，脱敏")
  private String email;

  /**
   * 联系电话
   */
  @Schema(description = "联系电话，脱敏")
  private String telephone;
}
