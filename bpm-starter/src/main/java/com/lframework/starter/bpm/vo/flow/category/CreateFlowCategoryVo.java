package com.lframework.starter.bpm.vo.flow.category;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateFlowCategoryVo implements BaseVo {

  public static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  private String name;

  /**
   * 父节点ID
   */
  @ApiModelProperty("父节点ID")
  private String parentId;
}
