package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.DefaultSysUserDept;
 import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysUserDeptMapper;
import com.lframework.starter.mybatis.service.system.SysUserDeptService;
import com.lframework.starter.mybatis.vo.system.dept.SysUserDeptSettingVo;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysUserDeptServiceImpl extends
    BaseMpServiceImpl<DefaultSysUserDeptMapper, DefaultSysUserDept>
    implements SysUserDeptService {

  @OpLog(type = DefaultOpLogType.OTHER, name = "用户设置部门，用户ID：{}，部门ID：{}", params = {"#vo.userId",
      "#vo.positionId"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysUserDeptSettingVo vo) {

    this.doSetting(vo);

    SysUserDeptService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(vo.getUserId());
  }

  @Cacheable(value = DefaultSysUserDept.CACHE_NAME, key = "@cacheVariables.tenantId() + #userId")
  @Override
  public List<DefaultSysUserDept> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  @Override
  public Boolean hasByDeptId(String deptId) {

    return doHasByDeptId(deptId);
  }

  protected void doSetting(SysUserDeptSettingVo vo) {

    Wrapper<DefaultSysUserDept> deleteWrapper = Wrappers.lambdaQuery(DefaultSysUserDept.class)
        .eq(DefaultSysUserDept::getUserId, vo.getUserId());
    getBaseMapper().delete(deleteWrapper);

    if (!CollectionUtil.isEmpty(vo.getDeptIds())) {
      for (String deptId : vo.getDeptIds()) {
        DefaultSysUserDept record = new DefaultSysUserDept();
        record.setId(IdUtil.getId());
        record.setUserId(vo.getUserId());
        record.setDeptId(deptId);

        getBaseMapper().insert(record);
      }
    }
  }

  protected List<DefaultSysUserDept> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }

  protected Boolean doHasByDeptId(String deptId) {

    return getBaseMapper().hasByDeptId(deptId) != null;
  }

  @CacheEvict(value = DefaultSysUserDept.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
