package com.lframework.starter.bpm.bo.flow.definition;

import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.service.FlowCategoryService;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FlowDefinitionSelectorBo extends BaseBo<FlowDefinitionWrapper> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 流程编号
   */
  @Schema(description = "流程编号")
  private String flowCode;

  /**
   * 流程名称
   */
  @Schema(description = "流程名称")
  private String flowName;

  /**
   * 流程分类
   */
  @Schema(description = "流程分类")
  private String categoryName;

  /**
   * 版本号
   */
  @Schema(description = "版本号")
  private String version;

  /**
   * 创建时间
   * @param dto
   */
  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  public FlowDefinitionSelectorBo(FlowDefinitionWrapper dto) {
    super(dto);
  }

  @Override
  protected void afterInit(FlowDefinitionWrapper dto) {
    FlowCategoryService flowCategoryService = ApplicationUtil.getBean(FlowCategoryService.class);
    FlowCategory flowCategory = flowCategoryService.findById(dto.getCategory());
    this.categoryName = flowCategory.getName();
  }
}
