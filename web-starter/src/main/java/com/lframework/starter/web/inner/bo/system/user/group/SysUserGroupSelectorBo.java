package com.lframework.starter.web.inner.bo.system.user.group;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysUserGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysUserGroupSelectorBo extends BaseBo<SysUserGroup> {

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

  public SysUserGroupSelectorBo() {

  }

  public SysUserGroupSelectorBo(SysUserGroup dto) {

    super(dto);
  }
}
