package com.lframework.starter.web.inner.bo.system.tenant;

import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TenantSelectorBo extends BaseBo<Tenant> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

  public TenantSelectorBo() {

  }

  public TenantSelectorBo(Tenant dto) {

    super(dto);
  }
}
