package com.lframework.starter.bpm.vo.flow.definition;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFlowDefinitionVo implements BaseVo, Serializable {

  public static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "编号不能为空！")
  @IsCode
  private String code;

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
