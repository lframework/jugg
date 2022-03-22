package com.lframework.starter.web.vo;

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
  private Integer pageIndex;

  /**
   * 每页条数
   */
  private Integer pageSize;
}
