package com.lframework.starter.gen.vo.custom.selector;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenCustomSelectorSelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}
