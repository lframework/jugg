package com.lframework.starter.bpm.bo.flow.category;

import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.service.FlowCategoryService;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DetailFlowCategoryBo extends BaseBo<FlowCategory> {

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

  /**
   * 父节点名称
   */
  @ApiModelProperty("父节点名称")
  private String parentName;

  public DetailFlowCategoryBo(FlowCategory dto) {
    super(dto);
  }

  @Override
  protected void afterInit(FlowCategory dto) {
    if (StringUtil.isNotEmpty(dto.getParentId())) {
      FlowCategoryService flowCategoryService = ApplicationUtil.getBean(FlowCategoryService.class);
      FlowCategory parent = flowCategoryService.findById(dto.getParentId());
      this.parentName = parent.getName();
    }
  }
}
