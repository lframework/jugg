package com.lframework.starter.bpm.vo.flow.category;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class UpdateFlowCategoryVo implements BaseVo, Serializable {

  public static final long serialVersionUID = 1L;
  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  private String id;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;
}
