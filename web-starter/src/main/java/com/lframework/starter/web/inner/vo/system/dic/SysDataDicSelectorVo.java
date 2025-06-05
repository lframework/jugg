package com.lframework.starter.web.inner.vo.system.dic;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysDataDicSelectorVo extends PageVo {

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

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;
}
