package com.lframework.starter.web.core.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 分页Vo
 *
 * @author zmj
 */
@Data
public abstract class PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 当前页码
   */
  @ApiModelProperty(value = "当前页码", required = true)
  private Integer pageIndex;

  /**
   * 每页条数
   */
  @ApiModelProperty(value = "每页条数", required = true)
  private Integer pageSize;
}
