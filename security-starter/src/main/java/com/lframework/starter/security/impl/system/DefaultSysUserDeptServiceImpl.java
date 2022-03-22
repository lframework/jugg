package com.lframework.starter.security.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.security.dto.system.dept.DefaultSysUserDeptDto;
import com.lframework.starter.security.entity.DefaultSysUserDept;
import com.lframework.starter.security.mappers.system.DefaultSysUserDeptMapper;
import com.lframework.starter.security.service.system.ISysUserDeptService;
import com.lframework.starter.security.vo.system.dept.SysUserDeptSettingVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysUserDeptServiceImpl implements ISysUserDeptService {

  @Autowired
  private DefaultSysUserDeptMapper defaultSysUserDeptMapper;

  @OpLog(type = OpLogType.OTHER, name = "用户设置部门，用户ID：{}，部门ID：{}", params = {"#vo.userId",
      "#vo.positionId"})
  @Transactional
  @Override
  public void setting(SysUserDeptSettingVo vo) {

    this.doSetting(vo);

    ISysUserDeptService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(vo.getUserId());
  }

  @Cacheable(value = DefaultSysUserDeptDto.CACHE_NAME, key = "#userId")
  @Override
  public List<DefaultSysUserDeptDto> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  @Override
  public Boolean hasByDeptId(String deptId) {

    return doHasByDeptId(deptId);
  }

  protected void doSetting(SysUserDeptSettingVo vo) {

    Wrapper<DefaultSysUserDept> deleteWrapper = Wrappers.lambdaQuery(DefaultSysUserDept.class)
        .eq(DefaultSysUserDept::getUserId, vo.getUserId());
    defaultSysUserDeptMapper.delete(deleteWrapper);

    if (!CollectionUtil.isEmpty(vo.getDeptIds())) {
      for (String deptId : vo.getDeptIds()) {
        DefaultSysUserDept record = new DefaultSysUserDept();
        record.setId(IdUtil.getId());
        record.setUserId(vo.getUserId());
        record.setDeptId(deptId);

        defaultSysUserDeptMapper.insert(record);
      }
    }
  }

  protected List<DefaultSysUserDeptDto> doGetByUserId(String userId) {

    return defaultSysUserDeptMapper.getByUserId(userId);
  }

  protected Boolean doHasByDeptId(String deptId) {

    return defaultSysUserDeptMapper.hasByDeptId(deptId) != null;
  }

  @CacheEvict(value = DefaultSysUserDeptDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}
