package com.lframework.starter.web.inner.vo.system.config;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 是否允许注册
   */
  @Schema(description = "是否允许注册", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择是否允许注册！")
  @TypeMismatch(message = "是否允许注册格式错误！")
  private Boolean allowRegist;

  /**
   * 是否允许手机号登录
   */
  @Schema(description = "是否允许手机号登录", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择是否允许手机号登录！")
  @TypeMismatch(message = "是否允许手机号登录格式错误！")
  private Boolean allowTelephoneLogin;

  /**
   * 手机号登录时的signName
   */
  private String telephoneLoginSignName;

  /**
   * 手机号登录时的templateCode
   */
  private String telephoneLoginTemplateCode;

  /**
   * 是否允许锁定用户
   */
  @Schema(description = "是否允许锁定用户", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择是否允许锁定用户！")
  @TypeMismatch(message = "是否允许锁定用户格式错误！")
  private Boolean allowLock;

  /**
   * 登录失败次数
   */
  @Schema(description = "登录失败次数")
  private Integer failNum;

  /**
   * 是否允许验证码
   */
  @Schema(description = "是否允许验证码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择是否允许验证码！")
  @TypeMismatch(message = "是否允许验证码格式错误！")
  private Boolean allowCaptcha;

  /**
   * 是否开启忘记密码
   */
  @Schema(description = "是否开启忘记密码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择是否开启忘记密码！")
  @TypeMismatch(message = "是否开启忘记密码格式错误！")
  private Boolean allowForgetPsw;

  /**
   * 忘记密码是否使用邮箱
   */
  @Schema(description = "忘记密码是否使用邮箱，allowForgetPsw == true时必填")
  @TypeMismatch(message = "忘记密码是否使用邮箱格式错误！")
  private Boolean forgetPswRequireMail;

  /**
   * 忘记密码是否使用短信
   */
  @Schema(description = "忘记密码是否使用短信，allowForgetPsw == true时必填")
  @TypeMismatch(message = "忘记密码是否使用短信格式错误！")
  private Boolean forgetPswRequireSms;

  /**
   * signName
   */
  @Schema(description = "signName，forgetPswRequireSms == true时必填")
  private String signName;

  /**
   * templateCode
   */
  @Schema(description = "templateCode，forgetPswRequireSms == true时必填")
  private String templateCode;
}
