package com.lframework.starter.web.inner.bo.system.role.category;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysRoleCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysRoleCategorySelectorBo extends BaseBo<SysRoleCategory> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  public SysRoleCategorySelectorBo() {
  }

  public SysRoleCategorySelectorBo(SysRoleCategory dto) {
    super(dto);
  }
}
