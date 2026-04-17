package com.lframework.starter.web.inner.bo.system.open;

import com.lframework.starter.web.inner.entity.SysOpenDomain;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysOpenDomainSelectorBo extends BaseBo<SysOpenDomain> {

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

  public SysOpenDomainSelectorBo() {
  }

  public SysOpenDomainSelectorBo(SysOpenDomain dto) {
    super(dto);
  }
}
