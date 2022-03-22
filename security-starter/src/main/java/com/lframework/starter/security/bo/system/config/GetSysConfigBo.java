package com.lframework.starter.security.bo.system.config;

import com.lframework.starter.security.dto.system.config.SysConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysConfigBo extends BaseBo<SysConfigDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 是否允许注册
   */
  private Boolean allowRegist;

  /**
   * 是否允许锁定用户
   */
  private Boolean allowLock;

  /**
   * 登录失败次数
   */
  private Integer failNum;

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

  /**
   * signName
   */
  private String signName;

  /**
   * templateCode
   */
  private String templateCode;

  public GetSysConfigBo(SysConfigDto dto) {
    super(dto);
  }
}
