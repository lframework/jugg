package com.lframework.starter.security.service.system;

import com.lframework.starter.security.dto.system.role.DefaultSysUserRoleDto;
import com.lframework.starter.security.vo.system.user.SysUserRoleSettingVo;
import com.lframework.starter.web.service.BaseService;
import java.util.List;

public interface ISysUserRoleService extends BaseService {

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
