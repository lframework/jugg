package com.lframework.starter.security.bo.system.config;

import com.lframework.starter.mybatis.dto.system.config.SysConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysConfigBo extends BaseBo<SysConfigDto> {

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

  public GetSysConfigBo(SysConfigDto dto) {

    super(dto);
  }
}
