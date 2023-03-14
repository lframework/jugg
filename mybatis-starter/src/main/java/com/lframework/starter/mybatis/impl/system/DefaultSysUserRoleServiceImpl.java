package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.DefaultSysRole;
import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.entity.DefaultSysUserRole;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysUserRoleMapper;
import com.lframework.starter.mybatis.service.system.SysRoleService;
import com.lframework.starter.mybatis.service.system.SysUserRoleService;
import com.lframework.starter.mybatis.service.system.SysUserService;
import com.lframework.starter.mybatis.vo.system.user.SysUserRoleSettingVo;
import com.lframework.starter.web.utils.IdUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysUserRoleServiceImpl extends
    BaseMpServiceImpl<DefaultSysUserRoleMapper, DefaultSysUserRole>
    implements SysUserRoleService {

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private SysRoleService sysRoleService;

  @OpLog(type = DefaultOpLogType.OTHER, name = "用户授权角色，用户ID：{}，角色ID：{}", params = {"#vo.userIds",
      "#vo.roleIds"}, loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysUserRoleSettingVo vo) {

    for (String userId : vo.getUserIds()) {
      this.doSetting(userId, vo.getRoleIds());
    }
  }

  @Override
  public List<DefaultSysUserRole> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  protected void doSetting(String userId, List<String> roleIds) {

    Wrapper<DefaultSysUserRole> deleteWrapper = Wrappers.lambdaQuery(DefaultSysUserRole.class)
        .eq(DefaultSysUserRole::getUserId, userId);
    getBaseMapper().delete(deleteWrapper);

    if (!CollectionUtil.isEmpty(roleIds)) {
      Set<String> roleIdSet = new HashSet<>(roleIds);

      for (String roleId : roleIdSet) {
        DefaultSysRole role = sysRoleService.findById(roleId);
        if (ObjectUtil.isNull(role)) {
          throw new DefaultClientException("角色不存在，请检查！");
        }

        DefaultSysUserRole record = new DefaultSysUserRole();
        record.setId(IdUtil.getId());
        record.setUserId(userId);
        record.setRoleId(role.getId());

        getBaseMapper().insert(record);
      }
    }
  }

  protected List<DefaultSysUserRole> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }
}
