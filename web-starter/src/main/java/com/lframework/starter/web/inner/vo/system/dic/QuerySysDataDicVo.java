package com.lframework.starter.web.inner.vo.system.dic;

import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuerySysDataDicVo extends SortPageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @ApiModelProperty(value = "租户ID", required = true)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

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
