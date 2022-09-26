package com.lframework.starter.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.gen.enums.GenRelaMode;
import com.lframework.starter.gen.enums.GenRelaType;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据对象明细
 * </p>
 *
 * @author zmj
 * @since 2022-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_data_obj_detail")
public class GenDataObjDetail extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "GenDataObjDetail";

  /**
   * ID
   */
  private String id;

  /**
   * 数据对象ID
   */
  private String dataObjId;

  /**
   * 主表字段
   */
  private String mainTableDetailIds;

  /**
   * 关联类型
   */
  private GenRelaType relaType;

  /**
   * 关联方式
   */
  private GenRelaMode relaMode;

  /**
   * 子表ID
   */
  private String subTableId;

  /**
   * 子表别名
   */
  private String subTableAlias;

  /**
   * 主表字段
   */
  private String subTableDetailIds;

  /**
   * 排序
   */
  private Integer orderNo;
}
