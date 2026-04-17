package com.lframework.starter.web.inner.vo.system.user;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录请求参数
 */
@Data
public class LoginVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID")
  private Integer tenantId;

  /**
   * 租户名称
   */
  @Schema(description = "租户名称")
  private String tenantName;

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
  @NotBlank(message = "密码不能为空！")
  private String password;

  /**
   * sn
   */
  @Schema(description = "sn，验证码流水号", requiredMode = Schema.RequiredMode.REQUIRED)
  private String sn;

  /**
   * 验证码
   */
  @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
  private String captcha;
}
