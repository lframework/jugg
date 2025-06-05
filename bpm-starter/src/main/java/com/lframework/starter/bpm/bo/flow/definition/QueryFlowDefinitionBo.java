package com.lframework.starter.bpm.bo.flow.definition;

import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.service.FlowCategoryService;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryFlowDefinitionBo extends BaseBo<FlowDefinitionWrapper> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 流程编号
   */
  @ApiModelProperty("流程编号")
  private String flowCode;

  /**
   * 流程名称
   */
  @ApiModelProperty("流程名称")
  private String flowName;

  /**
   * 流程分类
   */
  @ApiModelProperty("流程分类")
  private String categoryName;

  /**
   * 版本号
   */
  @ApiModelProperty("版本号")
  private String version;

  /**
   * 是否发布
   */
  @ApiModelProperty("是否发布")
  @EnumConvert
  private Integer isPublish;

  /**
   * 激活状态
   */
  @ApiModelProperty("激活状态")
  @EnumConvert
  private Integer activityStatus;

  /**
   * 创建时间
   * @param dto
   */
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  public QueryFlowDefinitionBo(FlowDefinitionWrapper dto) {
    super(dto);
  }

  @Override
  protected void afterInit(FlowDefinitionWrapper dto) {
    FlowCategoryService flowCategoryService = ApplicationUtil.getBean(FlowCategoryService.class);
    FlowCategory flowCategory = flowCategoryService.findById(dto.getCategory());
    this.categoryName = flowCategory.getName();
  }
}
