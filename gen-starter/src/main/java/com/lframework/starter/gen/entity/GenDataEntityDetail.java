package com.lframework.starter.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.gen.enums.GenDataType;
import com.lframework.starter.gen.enums.GenOrderType;
import com.lframework.starter.gen.enums.GenViewType;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据实体明细
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_data_entity_detail")
public class GenDataEntityDetail extends BaseEntity implements BaseDto {

  /**
   * ID
   */
  private String id;

  /**
   * 数据对象ID
   */
  private String entityId;

  /**
   * 字段显示名称
   */
  private String name;

  /**
   * 字段名称
   */
  private String columnName;

  /**
   * 是否主键
   */
  private Boolean isKey;

  /**
   * 数据类型
   */
  private GenDataType dataType;

  /**
   * 排序编号
   */
  private Integer columnOrder;

  /**
   * 备注
   */
  private String description;

  /**
   * 显示类型
   */
  private GenViewType viewType;

  /**
   * 是否内置枚举
   */
  private Boolean fixEnum;

  /**
   * 后端枚举名
   */
  private String enumBack;

  /**
   * 前端枚举名
   */
  private String enumFront;

  /**
   * 正则表达式
   */
  private String regularExpression;

  /**
   * 是否排序字段
   */
  private Boolean isOrder;

  /**
   * 排序类型
   */
  private GenOrderType orderType;
}
