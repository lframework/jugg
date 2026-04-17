package com.lframework.starter.web.inner.vo.system.user;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.web.core.components.validation.Regex;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistUserVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户名
   */
  @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "用户名不能为空！")
  private String username;

  /**
   * 密码
   */
  @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
  @Regex(regexp = PatternPool.PATTERN_STR_PASSWORD, message = "密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线")
  @NotBlank(message = "密码不能为空！")
  private String password;

  /**
   * 姓名
   */
  @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "姓名不能为空！")
  private String name;

  /**
   * 邮箱
   */
  @Schema(description = "邮箱")
  @Regex(regexp = PatternPool.PATTERN_STR_EMAIL, message = "邮箱地址格式不正确！")
  private String email;

  /**
   * 联系电话
   */
  @Schema(description = "联系电话")
  @Regex(regexp = PatternPool.PATTERN_STR_CN_TEL, message = "联系电话格式不正确！")
  private String telephone;
}
