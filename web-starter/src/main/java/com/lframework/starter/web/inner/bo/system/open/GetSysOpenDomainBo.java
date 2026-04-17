package com.lframework.starter.web.inner.bo.system.open;

import com.lframework.starter.web.inner.entity.SysOpenDomain;
import com.lframework.starter.web.core.annotations.convert.EncryptConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetSysOpenDomainBo extends BaseBo<SysOpenDomain> {

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
   * API密钥
   */
  @Schema(description = "API密钥")
  @EncryptConvert
  private String apiSecret;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID")
  private Integer tenantId;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public GetSysOpenDomainBo() {

  }

  public GetSysOpenDomainBo(SysOpenDomain dto) {

    super(dto);
  }
}
