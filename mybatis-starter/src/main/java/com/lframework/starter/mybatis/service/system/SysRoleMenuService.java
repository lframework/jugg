package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.entity.DefaultSysRoleMenu;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.role.SysRoleMenuSettingVo;

public interface SysRoleMenuService extends BaseMpService<DefaultSysRoleMenu> {

  /**
   * 授权角色菜单
   *
   * @param vo
   */
  void setting(SysRoleMenuSettingVo vo);
}
