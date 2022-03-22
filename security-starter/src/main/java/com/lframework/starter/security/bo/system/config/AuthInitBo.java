package com.lframework.starter.security.bo.system.config;

import com.lframework.starter.security.dto.system.config.SysConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthInitBo extends BaseBo<SysConfigDto> {

  /**
   * 是否允许注册
   */
  private Boolean allowRegist;

  /**
   * 是否允许验证码
   */
  private Boolean allowCaptcha;

  /**
   * 是否开启忘记密码
   */
  private Boolean allowForgetPsw;

  /**
   * 忘记密码是否使用邮箱
   */
  private Boolean forgetPswRequireMail;

  /**
   * 忘记密码是否使用短信
   */
  private Boolean forgetPswRequireSms;

  public AuthInitBo(SysConfigDto dto) {
    super(dto);
  }
}
