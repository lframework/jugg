package com.lframework.starter.bpm.vo.flow.definition;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateFlowDefinitionVo implements BaseVo, Serializable {

  public static final long serialVersionUID = 1L;

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
   * 分类ID
   */
  @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "分类ID不能为空！")
  private String categoryId;
}
