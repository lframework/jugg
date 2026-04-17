package com.lframework.starter.bpm.bo.flow.definition;

import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.service.FlowCategoryService;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DetailFlowDefinitionBo extends BaseBo<FlowDefinitionWrapper> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 流程编号
   */
  @Schema(description = "流程编号")
  private String code;

  /**
   * 流程名称
   */
  @Schema(description = "流程名称")
  private String name;

  /**
   * 流程分类ID
   */
  @Schema(description = "流程分类ID")
  private String categoryId;

  /**
   * 流程分类
   */
  @Schema(description = "流程分类")
  private String categoryName;

  /**
   * 是否发布
   */
  @Schema(description = "是否发布")
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
