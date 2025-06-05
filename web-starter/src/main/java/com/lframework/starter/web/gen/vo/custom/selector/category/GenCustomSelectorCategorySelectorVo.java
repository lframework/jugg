package com.lframework.starter.web.gen.vo.custom.selector.category;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomSelectorCategorySelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;
}
