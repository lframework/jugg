package com.lframework.starter.web.inner.vo.system.tenant;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryTenantVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @ApiModelProperty("租户ID")
  private String tenantId;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}
