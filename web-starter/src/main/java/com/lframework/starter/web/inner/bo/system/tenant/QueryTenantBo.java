package com.lframework.starter.web.inner.bo.system.tenant;

import com.lframework.starter.web.core.annotations.constants.EncryType;
import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.core.annotations.convert.EncryptConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryTenantBo extends BaseBo<Tenant> {

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
   * 绑定域名
   */
  @Schema(description = "绑定域名")
  private String serverName;

  /**
   * JdbcUrl
   */
  @Schema(description = "JdbcUrl")
  @EncryptConvert
  private String jdbcUrl;

  /**
   * Jdbc用户名
   */
  @Schema(description = "Jdbc用户名")
  @EncryptConvert
  private String jdbcUsername;

  /**
   * Jdbc密码
   */
  @Schema(description = "Jdbc密码")
  @EncryptConvert(type = EncryType.PASSWORD)
  private String jdbcPassword;

  /**
   * 是否为平台管理租户
   */
  @Schema(description = "是否为平台管理租户")
  private Boolean isPlatform;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

  public QueryTenantBo(Tenant dto) {
    super(dto);
  }
}
