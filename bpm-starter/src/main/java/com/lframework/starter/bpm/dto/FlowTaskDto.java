package com.lframework.starter.bpm.dto;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FlowTaskDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  private Long taskId;

  /**
   * 任务标题
   */
  private String title;

  /**
   * 业务ID
   */
  private String businessId;

  /**
   * 节点编号
   */
  private String nodeCode;

  /**
   * 节点名称
   */
  private String nodeName;

  /**
   * 节点类型
   */
  private Integer nodeType;

  /**
   * 流程状态
   */
  private String flowStatus;

  /**
   * 流程定义ID
   */
  private Long flowId;

  /**
   * 流程定义编号
   */
  private String flowCode;

  /**
   * 流程定义名称
   */
  private String flowName;

  /**
   * 流程定义分类ID
   */
  private String categoryId;

  /**
   * 流程实例ID
   */
  private Long instanceId;

  /**
   * 流程实例扩展字段
   */
  private String ext;

  /**
   * 流程发起人ID
   */
  private String startById;

  /**
   * 流程发起时间
   */
  private LocalDateTime startTime;

  /**
   * 流程结束时间
   */
  private LocalDateTime endTime;
}
