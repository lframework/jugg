package com.lframework.starter.web.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.components.oplog.SystemOpLogType;
import com.lframework.starter.web.inner.entity.SysUserRole;
import com.lframework.starter.web.inner.mappers.system.SysUserRoleMapper;
import com.lframework.starter.web.inner.service.system.SysUserRoleService;
import com.lframework.starter.web.inner.vo.system.user.SysUserRoleSettingVo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserRoleServiceImpl extends
    BaseMpServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService {

  @OpLog(type = SystemOpLogType.class, name = "用户授权角色，用户ID：{}，角色ID：{}，处理方式：{}", params = {
      "#vo.userIds",
      "#vo.roleIds", "#vo.handleType"}, loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysUserRoleSettingVo vo) {

    this.doSetting(vo);
  }

  @Override
  public List<SysUserRole> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  @Override
  public List<SysUserRole> getByRoleId(String roleId) {

    return doGetByRoleId(roleId);
  }

  protected void doSetting(SysUserRoleSettingVo vo) {
    List<SysUserRole> records = new ArrayList<>();
    if (vo.getHandleType() == 1) {
      // 新增
      if (CollectionUtil.isNotEmpty(vo.getRoleIds())) {
        Wrapper<SysUserRole> deleteWrapper = Wrappers.lambdaQuery(SysUserRole.class)
            .in(SysUserRole::getUserId, vo.getUserIds())
            .in(SysUserRole::getRoleId, vo.getRoleIds());
        getBaseMapper().delete(deleteWrapper);
        for (String userId : vo.getUserIds()) {
          for (String roleId : vo.getRoleIds()) {
            SysUserRole record = new SysUserRole();
            record.setId(IdUtil.getId());
            record.setUserId(userId);
            record.setRoleId(roleId);
            records.add(record);
          }
        }
      }
    } else if (vo.getHandleType() == 2) {
      // 替换
      Wrapper<SysUserRole> deleteWrapper = Wrappers.lambdaQuery(SysUserRole.class)
          .in(SysUserRole::getUserId, vo.getUserIds());
      getBaseMapper().delete(deleteWrapper);
      if (CollectionUtil.isNotEmpty(vo.getRoleIds())) {
        for (String userId : vo.getUserIds()) {
          for (String roleId : vo.getRoleIds()) {
            SysUserRole record = new SysUserRole();
            record.setId(IdUtil.getId());
            record.setUserId(userId);
            record.setRoleId(roleId);
            records.add(record);
          }
        }
      }
    } else if (vo.getHandleType() == 3) {
      // 删除
      if (CollectionUtil.isNotEmpty(vo.getRoleIds())) {
        Wrapper<SysUserRole> deleteWrapper = Wrappers.lambdaQuery(SysUserRole.class)
            .in(SysUserRole::getUserId, vo.getUserIds())
            .in(SysUserRole::getRoleId, vo.getRoleIds());
        getBaseMapper().delete(deleteWrapper);
      }
    }

    if (CollectionUtil.isNotEmpty(records)) {
      this.saveBatch(records);
    }
  }

  protected List<SysUserRole> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }

  protected List<SysUserRole> doGetByRoleId(String roleId) {

    return getBaseMapper().getByRoleId(roleId);
  }
}
