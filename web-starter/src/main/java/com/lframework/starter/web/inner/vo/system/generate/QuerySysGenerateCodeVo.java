package com.lframework.starter.web.inner.vo.system.generate;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class QuerySysGenerateCodeVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "规则ID")
  private Integer id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;
}
