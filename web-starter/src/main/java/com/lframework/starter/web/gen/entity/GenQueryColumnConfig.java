package com.lframework.starter.web.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.gen.enums.GenQueryWidthType;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.starter.web.core.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-12-10
 */
@Data
@TableName("gen_query_column_config")
public class GenQueryColumnConfig extends BaseEntity implements BaseDto {

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
