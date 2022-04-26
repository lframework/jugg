package com.lframework.starter.security.bo.auth;

import com.lframework.starter.mybatis.entity.SysConfig;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthInitBo extends BaseBo<SysConfig> {

  /**
   * 是否允许注册
   */
  @ApiModelProperty("是否允许注册")
  private Boolean allowRegist;

  /**
   * 是否允许手机号登录
   */
  @ApiModelProperty("是否允许手机号登录")
  private Boolean allowTelephoneLogin;

  /**
   * 是否允许验证码
   */
  @ApiModelProperty("是否允许验证码")
  private Boolean allowCaptcha;

  /**
   * 是否开启忘记密码
   */
  @ApiModelProperty("是否开启忘记密码")
  private Boolean allowForgetPsw;

  /**
   * 忘记密码是否使用邮箱
   */
  @ApiModelProperty("忘记密码是否使用邮箱")
  private Boolean forgetPswRequireMail;

  /**
   * 忘记密码是否使用短信
   */
  @ApiModelProperty("忘记密码是否使用短信")
  private Boolean forgetPswRequireSms;

  public AuthInitBo(SysConfig dto) {

    super(dto);
  }
}
