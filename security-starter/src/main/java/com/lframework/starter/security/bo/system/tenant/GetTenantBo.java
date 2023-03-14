package com.lframework.starter.security.bo.system.tenant;

import com.lframework.starter.mybatis.entity.Tenant;
import com.lframework.starter.web.annotations.convert.EncryptConvert;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetTenantBo extends BaseBo<Tenant> {

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
   * JdbcUrl
   */
  @ApiModelProperty("JdbcUrl")
  @EncryptConvert
  private String jdbcUrl;

  /**
   * Jdbc用户名
   */
  @ApiModelProperty("Jdbc用户名")
  @EncryptConvert
  private String jdbcUsername;

  /**
   * Jdbc密码
   */
  @ApiModelProperty("Jdbc密码")
  @EncryptConvert
  private String jdbcPassword;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public GetTenantBo(Tenant dto) {
    super(dto);
  }
}
