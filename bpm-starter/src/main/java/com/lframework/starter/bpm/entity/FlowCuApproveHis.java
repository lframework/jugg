package com.lframework.starter.bpm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.bpm.enums.FlowSkipType;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("flow_cu_approve_his")
public class FlowCuApproveHis extends BaseEntity implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private Long id;

  /**
   * 流程定义ID
   */
  private Long definitionId;

  /**
   * 流程实例ID
   */
  private Long instanceId;

  /**
   * 节点编码
   */
  private String nodeCode;

  /**
   * 节点名称
   */
  private String nodeName;

  /**
   * 任务ID
   */
  private Long taskId;

  /**
   * 创建人ID 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /**
   * 创建人 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 说明
   */
  private String message;

  /**
   * 审批类型
   */
  private FlowSkipType skipType;
}
