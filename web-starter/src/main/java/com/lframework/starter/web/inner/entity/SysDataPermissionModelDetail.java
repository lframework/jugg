package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.starter.web.inner.enums.system.SysDataPermissionModelDetailInputType;
import lombok.Data;

/**
 * <p>
 * 数据权限模型明细
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("sys_data_permission_model_detail")
public class SysDataPermissionModelDetail extends BaseEntity implements BaseDto {

  /**
   * ID
   */
  @TableId(type = IdType.AUTO)
  private Integer id;

  /**
   * 名称
   */
  private String name;

  /**
   * 模型ID
   */
  private Integer modelId;

  /**
   * 条件
   */
  private String conditionType;

  /**
   * 输入类型
   */
  private SysDataPermissionModelDetailInputType inputType;

  /**
   * 表名
   */
  private String tableName;

  /**
   * 字段名
   */
  private String columnName;

  /**
   * 前段枚举名
   */
  private String enumName;

  /**
   * SQL
   */
  private String sqlValue;
}
