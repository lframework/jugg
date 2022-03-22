package com.lframework.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("gen_detail_column_config")
public class GenDetailColumnConfig extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 列宽
   */
  private Integer span;

  /**
   * 排序编号
   */
  private Integer orderNo;


}
