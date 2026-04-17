package com.lframework.starter.web.inner.vo.system.user;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.web.core.components.validation.Regex;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 手机号登录请求参数
 */
@Data
public class TelephoneLoginVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 手机号
   */
  @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
  @Regex(regexp = PatternPool.PATTERN_STR_CN_TEL, message = "手机号格式不正确！")
  @NotBlank(message = "手机号不能为空！")
  private String telephone;

  /**
   * 验证码
   */
  @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "验证码不能为空！")
  private String captcha;
}
