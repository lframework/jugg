package com.lframework.starter.web.gen.vo.custom.page;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询自定义页面Vo
 */
@Data
public class QueryGenCustomPageVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private Integer id;

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
