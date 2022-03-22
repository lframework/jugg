package com.lframework.starter.security.dto.system.dept;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class DefaultSysDeptDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "DefaultSysDeptDto";

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
   * 简称
   */
  private String shortName;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;
}
