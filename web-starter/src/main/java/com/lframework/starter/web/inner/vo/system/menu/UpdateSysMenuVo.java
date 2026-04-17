package com.lframework.starter.web.inner.vo.system.menu;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysMenuVo extends CreateSysMenuVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 状态
   */
  @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "状态不能为空！")
  @TypeMismatch(message = "状态格式有误！")
  private Boolean available;
}
