package com.lframework.starter.security.bo.system.user;

import com.lframework.starter.security.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserSelectorBo extends BaseBo<DefaultSysUserDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 姓名
   */
  private String name;

  /**
   * 状态 1-在用 0停用
   */
  private Boolean available;

  public SysUserSelectorBo() {

  }

  public SysUserSelectorBo(DefaultSysUserDto dto) {

    super(dto);
  }
}
