package com.lframework.starter.web.inner.bo.system.user;

import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryUserRoleBo extends BaseBo<SysRole> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 权限
   */
  @Schema(description = "权限")
  private String permission;

  /**
   * 是否选中
   */
  @Schema(description = "是否选中")
  private Boolean selected = Boolean.FALSE;

  public QueryUserRoleBo() {

  }

  public QueryUserRoleBo(SysRole dto) {

    super(dto);
  }
}
