package com.lframework.starter.web.inner.bo.auth;

import com.lframework.starter.web.core.bo.SuperBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TenantRequireBo implements SuperBo {

  /**
   * 是否开启多租户
   */
  @ApiModelProperty("是否开启多租户")
  private Boolean enable;

  /**
   * 当前租户ID
   */
  @ApiModelProperty("当前租户ID")
  private Integer tenantId;
}
