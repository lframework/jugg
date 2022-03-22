package com.lframework.starter.security.bo.system.role;

import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleSelectorBo extends BaseBo<DefaultSysRoleDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 状态
   */
  private Boolean available;

  public SysRoleSelectorBo() {

  }

  public SysRoleSelectorBo(DefaultSysRoleDto dto) {

    super(dto);
  }
}
