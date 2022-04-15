package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.vo.system.role.SysRoleMenuSettingVo;
import com.lframework.starter.web.service.BaseService;

public interface ISysRoleMenuService extends BaseService {

  /**
   * 授权角色菜单
   *
   * @param vo
   */
  void setting(SysRoleMenuSettingVo vo);
}
