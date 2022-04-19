package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.dto.system.role.DefaultSysUserRoleDto;
import com.lframework.starter.mybatis.entity.DefaultSysUserRole;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.user.SysUserRoleSettingVo;
import java.util.List;

public interface ISysUserRoleService extends BaseMpService<DefaultSysUserRole> {

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
  List<DefaultSysUserRoleDto> getByUserId(String userId);
}
