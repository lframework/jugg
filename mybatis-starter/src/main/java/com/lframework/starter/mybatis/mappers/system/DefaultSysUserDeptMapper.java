package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.dto.system.dept.DefaultSysUserDeptDto;
import com.lframework.starter.mybatis.entity.DefaultSysUserDept;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
public interface DefaultSysUserDeptMapper extends BaseMapper<DefaultSysUserDept> {

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<DefaultSysUserDeptDto> getByUserId(String userId);

  /**
   * 根据部门ID查询是否存在
   *
   * @param deptId
   * @return
   */
  DefaultSysUserDeptDto hasByDeptId(String deptId);
}
