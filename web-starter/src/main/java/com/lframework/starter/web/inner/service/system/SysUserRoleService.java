package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.inner.entity.SysUserRole;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.vo.system.user.SysUserRoleSettingVo;
import java.util.List;

public interface SysUserRoleService extends BaseMpService<SysUserRole> {

  /**
   * 用户授权
   *
   * @param vo
   */
  void setting(SysUserRoleSettingVo vo);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<SysUserRole> getByUserId(String userId);

  /**
   * 根据角色ID查询
   * @param roleId
   * @return
   */
  List<SysUserRole> getByRoleId(String roleId);
}
