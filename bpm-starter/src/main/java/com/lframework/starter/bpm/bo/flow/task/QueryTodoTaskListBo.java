package com.lframework.starter.bpm.bo.flow.task;

import com.lframework.starter.bpm.dto.FlowTaskDto;
import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.service.FlowCategoryService;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryTodoTaskListBo extends BaseBo<FlowTaskDto> {

  /**
   * 任务ID
   */
  @Schema(description = "任务ID")
  private String taskId;

  /**
   * 任务标题
   */
  @Schema(description = "任务标题")
  private String title;

  /**
   * 业务ID
   */
  @Schema(description = "业务ID")
  private String businessId;

  /**
   * 节点编号
   */
  @Schema(description = "节点编号")
  private String nodeCode;

  /**
   * 节点名称
   */
  @Schema(description = "节点名称")
  private String nodeName;

  /**
   * 节点类型
   */
  @Schema(description = "节点类型")
  private Integer nodeType;

  /**
   * 流程状态
   */
  @Schema(description = "流程状态")
  private String flowStatus;

  /**
   * 流程定义ID
   */
  @Schema(description = "流程定义ID")
  private String flowId;

  /**
   * 流程定义编号
   */
  @Schema(description = "流程定义编号")
  private String flowCode;

  /**
   * 流程定义名称
   */
  @Schema(description = "流程定义名称")
  private String flowName;

  /**
   * 流程定义分类ID
   */
  @Schema(description = "流程定义分类ID")
  private String categoryId;

  /**
   * 流程定义分类名称
   */
  @Schema(description = "流程定义分类名称")
  private String categoryName;

  /**
   * 流程实例ID
   */
  @Schema(description = "流程实例ID")
  private String instanceId;

  /**
   * 流程实例扩展字段
   */
  @Schema(description = "流程实例扩展字段")
  private String ext;

  /**
   * 流程发起人ID
   */
  @Schema(description = "流程发起人ID")
  private String startById;

  /**
   * 流程发起人
   */
  @Schema(description = "流程发起人")
  private String startBy;

  /**
   * 流程发起时间
   */
  @Schema(description = "流程发起时间")
  private LocalDateTime startTime;

  public QueryTodoTaskListBo(FlowTaskDto dto) {
    super(dto);
  }

  @Override
  protected void afterInit(FlowTaskDto dto) {
    FlowCategoryService flowCategoryService = ApplicationUtil.getBean(FlowCategoryService.class);
    FlowCategory flowCategory = flowCategoryService.findById(dto.getCategoryId());
    this.categoryName = flowCategory.getName();

    SysUserService sysUserService = ApplicationUtil.getBean(SysUserService.class);
    this.startBy = sysUserService.findById(dto.getStartById()).getName();
  }
}
