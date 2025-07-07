package com.lframework.starter.bpm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.bpm.enums.FlowDefinitionActivityStatus;
import com.lframework.starter.bpm.enums.FlowDefinitionIsPublish;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("flow_definition")
public class FlowDefinitionWrapper extends BaseEntity implements BaseDto, Serializable {

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

  @TableLogic(value = "0", delval = "1")
  private String delFlag;

  /**
   * 租户ID
   */
  private String tenantId;

  /**
   * 流程编码
   */
  private String flowCode;

  /**
   * 流程名称
   */
  private String flowName;

  /**
   * 流程类别
   */
  private String category;

  /**
   * 流程版本
   */
  private String version;

  /**
   * 是否发布（0未开启 1开启）
   */
  private FlowDefinitionIsPublish isPublish;

  /**
   * 审批表单是否自定义（Y是 2否）
   */
  private String formCustom;

  /**
   * 审批表单是否自定义（Y是 2否）
   */
  private String formPath;

  /**
   * 流程激活状态（0挂起 1激活）
   */
  private FlowDefinitionActivityStatus activityStatus;

  /**
   * 监听器类型
   */
  private String listenerType;

  /**
   * 监听器路径
   */
  private String listenerPath;

  /**
   * 扩展字段，预留给业务系统使用
   */
  private String ext;
}
