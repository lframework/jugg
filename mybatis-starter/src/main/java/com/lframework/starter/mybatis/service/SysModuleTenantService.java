package com.lframework.starter.mybatis.service;

import com.lframework.starter.mybatis.entity.SysModuleTenant;
import com.lframework.starter.mybatis.vo.system.module.SysModuleTenantVo;
import java.util.List;

public interface SysModuleTenantService extends BaseMpService<SysModuleTenant> {

  /**
   * 根据租户ID查询可用模块ID
   *
   * @param tenantId
   * @return
   */
  List<Integer> getAvailableModuleIdsByTenantId(Integer tenantId);

  /**
   * 根据租户ID查询
   * @param tenantId
   * @return
   */
  List<SysModuleTenant> getByTenantId(Integer tenantId);

  /**
   * 设置模块
   * @param vo
   */
  void setting(SysModuleTenantVo vo);
}
