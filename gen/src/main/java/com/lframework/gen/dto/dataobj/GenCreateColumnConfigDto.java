package com.lframework.gen.dto.dataobj;

import com.lframework.gen.components.CreateColumnConfig;
import com.lframework.starter.web.dto.BaseDto;
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
