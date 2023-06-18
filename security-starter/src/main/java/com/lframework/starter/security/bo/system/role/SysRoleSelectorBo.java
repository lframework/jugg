package com.lframework.starter.security.bo.system.role;

import com.lframework.starter.mybatis.entity.DefaultSysRole;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysRoleSelectorBo extends BaseBo<DefaultSysRole> {

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

  public SysRoleSelectorBo() {

  }

  public SysRoleSelectorBo(DefaultSysRole dto) {

    super(dto);
  }
}
