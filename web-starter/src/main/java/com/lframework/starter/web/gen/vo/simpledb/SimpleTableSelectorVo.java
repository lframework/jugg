package com.lframework.starter.web.gen.vo.simpledb;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimpleTableSelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;
}
