package com.lframework.starter.web.inner.vo.system.open;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysOpenDomainVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "ID不能为空！")
  private Long id;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID")
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 状态
   */
  @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "状态不能为空！")
  private Boolean available;

}
