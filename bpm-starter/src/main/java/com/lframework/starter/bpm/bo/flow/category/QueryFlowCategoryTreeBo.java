package com.lframework.starter.bpm.bo.flow.category;

import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryFlowCategoryTreeBo extends BaseBo<FlowCategory> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;
  /**
   * 流程类型名称
   */
  @ApiModelProperty("流程类型名称")
  private String name;
  /**
   * 父节点ID
   */
  @ApiModelProperty("父节点ID")
  private String parentId;

  public QueryFlowCategoryTreeBo(FlowCategory dto) {
    super(dto);
  }
}
