package com.lframework.starter.bpm.bo.flow.task;

import cn.hutool.core.date.BetweenFormatter.Level;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.lframework.starter.bpm.dto.FlowTaskDto;
import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.service.FlowCategoryService;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryInstanceListBo extends BaseBo<FlowTaskDto> {

  /**
   * 任务标题
   */
  @ApiModelProperty("任务标题")
  private String title;

  /**
   * 业务ID
   */
  @ApiModelProperty("业务ID")
  private String businessId;

  /**
   * 节点编号
   */
  @ApiModelProperty("节点编号")
  private String nodeCode;

  /**
   * 节点名称
   */
  @ApiModelProperty("节点名称")
  private String nodeName;

  /**
   * 节点类型
   */
  @ApiModelProperty("节点类型")
  private Integer nodeType;

  /**
   * 流程状态
   */
  @ApiModelProperty("流程状态")
  private String flowStatus;

  /**
   * 流程定义ID
   */
  @ApiModelProperty("流程定义ID")
  private String flowId;

  /**
   * 流程定义编号
   */
  @ApiModelProperty("流程定义编号")
  private String flowCode;

  /**
   * 流程定义名称
   */
  @ApiModelProperty("流程定义名称")
  private String flowName;

  /**
   * 流程定义分类ID
   */
  @ApiModelProperty("流程定义分类ID")
  private String categoryId;

  /**
   * 流程定义分类名称
   */
  @ApiModelProperty("流程定义分类名称")
  private String categoryName;

  /**
   * 流程实例ID
   */
  @ApiModelProperty("流程实例ID")
  private String instanceId;

  /**
   * 流程实例扩展字段
   */
  @ApiModelProperty("流程实例扩展字段")
  private String ext;

  /**
   * 流程发起人ID
   */
  @ApiModelProperty("流程发起人ID")
  private String startById;

  /**
   * 流程发起人
   */
  @ApiModelProperty("流程发起人")
  private String startBy;

  /**
   * 流程发起时间
   */
  @ApiModelProperty("流程发起时间")
  private LocalDateTime startTime;

  /**
   * 流程结束时间
   */
  @ApiModelProperty("流程结束时间")
  private LocalDateTime endTime;

  /**
   * 流程执行时间
   */
  @ApiModelProperty("流程执行时间")
  private String executeTime;

  public QueryInstanceListBo(FlowTaskDto dto) {
    super(dto);
  }

  @Override
  protected void afterInit(FlowTaskDto dto) {
    FlowCategoryService flowCategoryService = ApplicationUtil.getBean(FlowCategoryService.class);
    FlowCategory flowCategory = flowCategoryService.findById(dto.getCategoryId());
    this.categoryName = flowCategory.getName();

    SysUserService sysUserService = ApplicationUtil.getBean(SysUserService.class);
    this.startBy = sysUserService.findById(dto.getStartById()).getName();

    if (dto.getEndTime() != null) {
      this.executeTime = DateUtil.formatBetween(
          LocalDateTimeUtil.between(dto.getStartTime(), dto.getEndTime()).getSeconds() * 1000L,
          Level.SECOND);
    }
  }
}
