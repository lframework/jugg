package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.inner.entity.SysRoleMenu;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.vo.system.role.SysRoleMenuSettingVo;

public interface SysRoleMenuService extends BaseMpService<SysRoleMenu> {

  /**
   * 授权角色菜单
   *
   * @param vo
   */
  void setting(SysRoleMenuSettingVo vo);
}
