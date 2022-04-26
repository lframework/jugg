package com.lframework.starter.security.bo.system.config;

import com.lframework.starter.mybatis.entity.SysConfig;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysConfigBo extends BaseBo<SysConfig> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

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
   * 手机号登录时的signName
   */
  @ApiModelProperty("手机号登录时的signName")
  private String telephoneLoginSignName;

  /**
   * 手机号登录时的templateCode
   */
  @ApiModelProperty("手机号登录时的templateCode")
  private String telephoneLoginTemplateCode;

  /**
   * 是否允许锁定用户
   */
  @ApiModelProperty("是否允许锁定用户")
  private Boolean allowLock;

  /**
   * 登录失败次数
   */
  @ApiModelProperty("登录失败次数")
  private Integer failNum;

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

  /**
   * signName
   */
  @ApiModelProperty("signName")
  private String signName;

  /**
   * templateCode
   */
  @ApiModelProperty("templateCode")
  private String templateCode;

  public GetSysConfigBo(SysConfig dto) {

    super(dto);
  }
}
