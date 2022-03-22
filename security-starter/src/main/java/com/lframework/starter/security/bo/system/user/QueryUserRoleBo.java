package com.lframework.starter.security.bo.system.user;

import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryUserRoleBo extends BaseBo<DefaultSysRoleDto> {

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
   * 权限
   */
  private String permission;

  /**
   * 是否选中
   */
  private Boolean selected = Boolean.FALSE;

  public QueryUserRoleBo() {

  }

  public QueryUserRoleBo(DefaultSysRoleDto dto) {

    super(dto);
  }
}
