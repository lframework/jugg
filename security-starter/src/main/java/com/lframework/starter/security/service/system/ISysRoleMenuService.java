package com.lframework.starter.security.service.system;

import com.lframework.starter.security.vo.system.role.SysRoleMenuSettingVo;
import com.lframework.starter.web.service.BaseService;

public interface ISysRoleMenuService extends BaseService {

  /**
   * 授权角色菜单
   *
   * @param vo
   */
  void setting(SysRoleMenuSettingVo vo);
}
