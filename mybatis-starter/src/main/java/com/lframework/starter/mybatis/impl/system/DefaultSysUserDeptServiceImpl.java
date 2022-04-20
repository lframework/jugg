package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.dto.system.dept.DefaultSysUserDeptDto;
import com.lframework.starter.mybatis.entity.DefaultSysUserDept;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysUserDeptMapper;
import com.lframework.starter.mybatis.service.system.ISysUserDeptService;
import com.lframework.starter.mybatis.vo.system.dept.SysUserDeptSettingVo;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysUserDeptServiceImpl extends
    BaseMpServiceImpl<DefaultSysUserDeptMapper, DefaultSysUserDept>
    implements ISysUserDeptService {

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

  protected List<DefaultSysUserDeptDto> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }

  protected Boolean doHasByDeptId(String deptId) {

    return getBaseMapper().hasByDeptId(deptId) != null;
  }

  @CacheEvict(value = DefaultSysUserDeptDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}
