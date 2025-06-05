package com.lframework.starter.web.inner.mappers.system;

import com.lframework.starter.web.inner.entity.SysUserDept;
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
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<SysUserDept> getByUserId(String userId);

  /**
   * 根据部门ID查询
   *
   * @param deptId
   * @return
   */
  List<SysUserDept> getByDeptId(String deptId);

  /**
   * 根据部门ID查询是否存在
   *
   * @param deptId
   * @return
   */
  SysUserDept hasByDeptId(String deptId);
}
