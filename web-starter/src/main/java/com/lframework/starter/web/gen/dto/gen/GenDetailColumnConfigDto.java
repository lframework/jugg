package com.lframework.starter.web.gen.dto.gen;

import com.lframework.starter.web.gen.components.DetailColumnConfig;
import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class GenDetailColumnConfigDto implements BaseDto, DetailColumnConfig, Serializable {

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
