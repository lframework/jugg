package com.lframework.starter.gen.vo.custom.list;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询自定义列表Vo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryGenCustomListVo extends PageVo {

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
