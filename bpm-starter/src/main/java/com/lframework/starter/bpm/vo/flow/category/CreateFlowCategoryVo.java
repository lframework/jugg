package com.lframework.starter.bpm.vo.flow.category;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateFlowCategoryVo implements BaseVo {

  public static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;

  /**
   * 父节点ID
   */
  @Schema(description = "父节点ID")
  private String parentId;
}
