package com.lframework.starter.web.inner.service;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysModuleTenant;
import com.lframework.starter.web.inner.vo.system.module.SysModuleTenantVo;
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
