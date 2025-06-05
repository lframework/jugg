package com.lframework.starter.web.gen.dto.gen;

import com.lframework.starter.web.gen.components.CreateColumnConfig;
import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class GenCreateColumnConfigDto implements BaseDto, CreateColumnConfig, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 是否必填
   */
  private Boolean required;

  /**
   * 排序编号
   */
  private Integer orderNo;
}
