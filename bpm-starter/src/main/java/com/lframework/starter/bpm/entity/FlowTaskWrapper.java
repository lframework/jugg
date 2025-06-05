package com.lframework.starter.bpm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("flow_task")
public class FlowTaskWrapper extends BaseEntity implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;

  /**
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;

  /**
   * 租户ID
   */
  private String tenantId;

  /**
   * 删除标记
   */
  @TableLogic(value = "0", delval = "1")
  private String delFlag;

  /**
   * 对应flow_definition表的id
   */
  private Long definitionId;

  /**
   * 流程实例表id
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
   * 节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
   */
  private Integer nodeType;

  /**
   * 流程状态（0待提交 1审批中 2审批通过 4终止 5作废 6撤销 8已完成 9已退回 10失效 11拿回）
   */
  private String flowStatus;

  /**
   * 审批表单是否自定义（Y是 2否）
   */
  private String formCustom;

  /**
   * 审批表单
   */
  private String formPath;
}
