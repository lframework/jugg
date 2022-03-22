package com.lframework.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.gen.enums.GenQueryWidthType;
import com.lframework.starter.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_query_column_config")
public class GenQueryColumnConfig extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 宽度类型
   */
  private GenQueryWidthType widthType;

  /**
   * 宽度
   */
  private Integer width;

  /**
   * 是否页面排序
   */
  private Boolean sortable;

  /**
   * 排序编号
   */
  private Integer orderNo;


}
