package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.entity.DefaultSysUserDept;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.dept.SysUserDeptSettingVo;
import java.util.List;

public interface SysUserDeptService extends BaseMpService<DefaultSysUserDept> {

  /**
   * 设置部门
   *
   * @param vo
   */
  void setting(SysUserDeptSettingVo vo);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<DefaultSysUserDept> getByUserId(String userId);

  /**
   * 根据部门ID查询是否存在
   *
   * @param deptId
   * @return
   */
  Boolean hasByDeptId(String deptId);
}
