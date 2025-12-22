package com.lframework.starter.web.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.components.oplog.SystemOpLogType;
import com.lframework.starter.web.inner.entity.SysUserDept;
import com.lframework.starter.web.inner.mappers.system.SysUserDeptMapper;
import com.lframework.starter.web.inner.service.system.SysUserDeptService;
import com.lframework.starter.web.inner.vo.system.dept.SysUserDeptSettingVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserDeptServiceImpl extends
    BaseMpServiceImpl<SysUserDeptMapper, SysUserDept> implements SysUserDeptService {

  @OpLog(type = SystemOpLogType.class, name = "用户设置部门，用户ID：{}，部门ID：{}，操作类型：{}", params = {
      "#vo.userIds", "#vo.deptIds", "#vo.handleType"}, loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysUserDeptSettingVo vo) {

    this.doSetting(vo);

    SysUserDeptService thisService = getThis(this.getClass());
    thisService.cleanCacheByKeys(vo.getUserIds());
  }

  @Cacheable(value = SysUserDept.CACHE_NAME, key = "@cacheVariables.tenantId() + #userId")
  @Override
  public List<SysUserDept> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  @Override
  public List<SysUserDept> getByDeptId(String deptId) {

    return doGetByDeptId(deptId);
  }

  @Override
  public Boolean hasByDeptId(String deptId) {

    return doHasByDeptId(deptId);
  }

  protected void doSetting(SysUserDeptSettingVo vo) {
    List<SysUserDept> records = new ArrayList<>();
    if (vo.getHandleType() == 1) {
      // 新增
      if (CollectionUtil.isNotEmpty(vo.getDeptIds())) {
        Wrapper<SysUserDept> deleteWrapper = Wrappers.lambdaQuery(SysUserDept.class)
            .in(SysUserDept::getUserId, vo.getUserIds())
            .in(SysUserDept::getDeptId, vo.getDeptIds());
        getBaseMapper().delete(deleteWrapper);
        for (String userId : vo.getUserIds()) {
          for (String deptId : vo.getDeptIds()) {
            SysUserDept record = new SysUserDept();
            record.setId(IdUtil.getId());
            record.setUserId(userId);
            record.setDeptId(deptId);
            records.add(record);
          }
        }
      }
    } else if (vo.getHandleType() == 2) {
      // 替换
      Wrapper<SysUserDept> deleteWrapper = Wrappers.lambdaQuery(SysUserDept.class)
          .in(SysUserDept::getUserId, vo.getUserIds());
      getBaseMapper().delete(deleteWrapper);
      if (CollectionUtil.isNotEmpty(vo.getDeptIds())) {
        for (String userId : vo.getUserIds()) {
          for (String deptId : vo.getDeptIds()) {
            SysUserDept record = new SysUserDept();
            record.setId(IdUtil.getId());
            record.setUserId(userId);
            record.setDeptId(deptId);
            records.add(record);
          }
        }
      }
    } else if (vo.getHandleType() == 3) {
      // 删除
      if (CollectionUtil.isNotEmpty(vo.getDeptIds())) {
        Wrapper<SysUserDept> deleteWrapper = Wrappers.lambdaQuery(SysUserDept.class)
            .in(SysUserDept::getUserId, vo.getUserIds())
            .in(SysUserDept::getDeptId, vo.getDeptIds());
        getBaseMapper().delete(deleteWrapper);
      }
    }

    if (CollectionUtil.isNotEmpty(records)) {
      this.saveBatch(records);
    }
  }

  protected List<SysUserDept> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }

  protected List<SysUserDept> doGetByDeptId(String deptId) {

    return getBaseMapper().getByDeptId(deptId);
  }

  protected Boolean doHasByDeptId(String deptId) {

    return getBaseMapper().hasByDeptId(deptId) != null;
  }

  @CacheEvict(value = SysUserDept.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
