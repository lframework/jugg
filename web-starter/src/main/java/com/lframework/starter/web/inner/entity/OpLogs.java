package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 操作日志实体类
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("op_logs")
public class OpLogs extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 日志名称
   */
  private String name;

  /**
   * 类别
   */
  private Integer logType;

  /**
   * IP地址
   */
  private String ip;

  /**
   * 补充信息
   */
  private String extra;

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
}
