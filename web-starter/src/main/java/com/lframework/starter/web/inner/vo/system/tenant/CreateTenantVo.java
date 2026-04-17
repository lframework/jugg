package com.lframework.starter.web.inner.vo.system.tenant;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTenantVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 绑定域名
   */
  @Schema(description = "绑定域名")
  private String serverName;

  /**
   * JdbcUrl
   */
  @Schema(description = "JdbcUrl", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入JdbcUrl！")
  private String jdbcUrl;

  /**
   * Jdbc用户名
   */
  @Schema(description = "Jdbc用户名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入Jdbc用户名！")
  private String jdbcUsername;

  /**
   * Jdbc密码
   */
  @Schema(description = "Jdbc密码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入Jdbc密码！")
  private String jdbcPassword;

  /**
   * 是否为平台管理租户
   */
  @Schema(description = "是否为平台管理租户", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择是否为平台管理租户！")
  private Boolean isPlatform;
}
