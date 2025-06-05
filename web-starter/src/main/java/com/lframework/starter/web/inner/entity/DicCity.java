package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-07
 */
@Data
@TableName("dic_city")
public class DicCity extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 父级ID
   */
  private String parentId;

  /**
   * 层级
   */
  private Integer level;

  /**
   * 状态
   */
  private Boolean available;
}
