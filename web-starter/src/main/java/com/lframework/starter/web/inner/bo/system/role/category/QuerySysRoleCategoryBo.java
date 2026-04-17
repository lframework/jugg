package com.lframework.starter.web.inner.bo.system.role.category;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysRoleCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QuerySysRoleCategoryBo extends BaseBo<SysRoleCategory> {

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


  public QuerySysRoleCategoryBo() {

  }

  public QuerySysRoleCategoryBo(SysRoleCategory dto) {

    super(dto);
  }
}
