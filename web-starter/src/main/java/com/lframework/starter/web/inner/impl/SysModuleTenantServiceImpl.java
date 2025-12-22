package com.lframework.starter.web.inner.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.entity.SysModule;
import com.lframework.starter.web.inner.entity.SysModuleTenant;
import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.inner.mappers.SysModuleTenantMapper;
import com.lframework.starter.web.inner.service.SysModuleService;
import com.lframework.starter.web.inner.service.SysModuleTenantService;
import com.lframework.starter.web.inner.service.TenantService;
import com.lframework.starter.web.inner.vo.system.module.SysModuleTenantVo;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DS("master")
@Service
public class SysModuleTenantServiceImpl extends
    BaseMpServiceImpl<SysModuleTenantMapper, SysModuleTenant> implements SysModuleTenantService {

  @Autowired
  private TenantService tenantService;

  @Autowired
  private SysModuleService sysModuleService;

  @Override
  public List<Integer> getAvailableModuleIdsByTenantId(Integer tenantId) {
    SysModuleTenantService thisService = getThis(getClass());
    return thisService.getByTenantId(tenantId).stream()
        .filter(t -> DateUtil.now().isBefore(t.getExpireTime())).map(SysModuleTenant::getModuleId)
        .collect(Collectors.toList());
  }

  @Cacheable(value = SysModuleTenant.CACHE_NAME, key = "#tenantId", unless = "#result == null or #result.isEmpty()")
  @Override
  public List<SysModuleTenant> getByTenantId(Integer tenantId) {
    Wrapper<SysModuleTenant> queryWrapper = Wrappers.lambdaQuery(SysModuleTenant.class)
        .eq(SysModuleTenant::getTenantId, tenantId);
    return this.list(queryWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysModuleTenantVo vo) {
    Wrapper<SysModuleTenant> queryWrapper = Wrappers.lambdaQuery(SysModuleTenant.class)
        .eq(SysModuleTenant::getTenantId, vo.getTenantId());
    this.remove(queryWrapper);

    if (CollectionUtil.isNotEmpty(vo.getModules())) {
      List<SysModuleTenant> records = vo.getModules().stream().map(t -> {

        SysModule sysModule = sysModuleService.getById(t.getModuleId());

        if (sysModule.getIsPlatform()) {
          Tenant tenant = tenantService.findById(vo.getTenantId());
          if (!tenant.getIsPlatform()) {
            throw new DefaultClientException(
                "租户【" + tenant.getName() + "】不是平台管理租户，不允许授权【" + sysModule.getName()
                    + "】模块！");
          }
        }
        SysModuleTenant record = new SysModuleTenant();
        record.setId(IdUtil.getId());
        record.setModuleId(t.getModuleId());
        record.setTenantId(vo.getTenantId());
        record.setExpireTime(t.getExpireTime());

        return record;
      }).collect(Collectors.toList());

      this.saveBatch(records);
    }
  }

  @CacheEvict(value = SysModuleTenant.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
