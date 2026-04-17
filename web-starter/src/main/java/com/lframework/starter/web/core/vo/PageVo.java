package com.lframework.starter.web.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "当前页码", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer pageIndex;

  /**
   * 每页条数
   */
  @Schema(description = "每页条数", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer pageSize;
}
