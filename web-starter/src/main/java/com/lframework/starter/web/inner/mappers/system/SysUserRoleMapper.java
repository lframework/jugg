package com.lframework.starter.web.inner.mappers.system;

import com.lframework.starter.web.inner.entity.SysUserRole;
import com.lframework.starter.web.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<SysUserRole> getByUserId(String userId);

  /**
   * 根据角色ID查询
   *
   * @param roleId
   * @return
   */
  List<SysUserRole> getByRoleId(String roleId);
}
