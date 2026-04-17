package com.lframework.starter.web.inner.vo.system.parameter;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysParameterVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "id不能为空！")
  private Long id;

  /**
   * 值
   */
  @Schema(description = "值")
  private String pmValue;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

}
