package com.lframework.starter.web.inner.dto.dic.city;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class DicCityDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "DicCityDto";

  public static final String SELECTOR_CACHE_NAME = "DicCitySelectorDto";

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
}
