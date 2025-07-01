package com.lframework.starter.web.inner.vo.system.generate;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QuerySysGenerateCodeVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("规则ID")
  private Integer id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;
}
