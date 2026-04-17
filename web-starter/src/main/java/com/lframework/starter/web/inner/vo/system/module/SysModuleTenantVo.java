package com.lframework.starter.web.inner.vo.system.module;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysModuleTenantVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Data
  public static class SysModuleVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "模块ID不能为空！")
    private Integer moduleId;

    /**
     * 过期时间
     */
    @Schema(description = "过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "过期时间不能为空！")
    private LocalDateTime expireTime;
  }

  /**
   * 租户ID
   */
  @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * 模块
   */
  @Schema(description = "模块")
  @Valid
  private List<SysModuleVo> modules;
}
