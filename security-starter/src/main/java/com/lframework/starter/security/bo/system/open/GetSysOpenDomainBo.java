package com.lframework.starter.security.bo.system.open;

import com.lframework.starter.mybatis.entity.SysOpenDomain;
import com.lframework.starter.web.annotations.convert.EncryptConvert;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysOpenDomainBo extends BaseBo<SysOpenDomain> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * API密钥
   */
  @ApiModelProperty("API密钥")
  @EncryptConvert
  private String apiSecret;

  /**
   * 租户ID
   */
  @ApiModelProperty("租户ID")
  private Integer tenantId;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetSysOpenDomainBo() {

  }

  public GetSysOpenDomainBo(SysOpenDomain dto) {

    super(dto);
  }
}