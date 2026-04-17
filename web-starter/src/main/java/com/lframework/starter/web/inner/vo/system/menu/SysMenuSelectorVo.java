package com.lframework.starter.web.inner.vo.system.menu;

import com.lframework.starter.web.inner.enums.system.SysMenuDisplay;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysMenuSelectorVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  @Schema(description = "类型")
  @IsEnum(message = "类型格式不正确！", enumClass = SysMenuDisplay.class)
  private Integer display;

  @Schema(description = "状态")
  private Boolean available;
}
