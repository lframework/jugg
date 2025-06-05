package com.lframework.starter.web.inner.bo.system.user.group;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysUserGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserGroupSelectorBo extends BaseBo<SysUserGroup> {

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

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public SysUserGroupSelectorBo() {

  }

  public SysUserGroupSelectorBo(SysUserGroup dto) {

    super(dto);
  }
}
