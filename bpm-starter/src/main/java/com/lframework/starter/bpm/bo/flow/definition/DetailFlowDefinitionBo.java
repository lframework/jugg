package com.lframework.starter.bpm.bo.flow.definition;

import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.service.FlowCategoryService;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DetailFlowDefinitionBo extends BaseBo<FlowDefinitionWrapper> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 流程编号
   */
  @ApiModelProperty("流程编号")
  private String code;

  /**
   * 流程名称
   */
  @ApiModelProperty("流程名称")
  private String name;

  /**
   * 流程分类ID
   */
  @ApiModelProperty("流程分类ID")
  private String categoryId;

  /**
   * 流程分类
   */
  @ApiModelProperty("流程分类")
  private String categoryName;

  /**
   * 是否发布
   */
  @ApiModelProperty("是否发布")
  @EnumConvert
  private Integer isPublish;

  public DetailFlowDefinitionBo(FlowDefinitionWrapper dto) {
    super(dto);
  }

  @Override
  protected void afterInit(FlowDefinitionWrapper dto) {
    FlowCategoryService flowCategoryService = ApplicationUtil.getBean(FlowCategoryService.class);
    FlowCategory flowCategory = flowCategoryService.findById(dto.getCategory());
    this.categoryId = flowCategory.getId();
    this.categoryName = flowCategory.getName();

    this.code = dto.getFlowCode();
    this.name = dto.getFlowName();
  }
}
