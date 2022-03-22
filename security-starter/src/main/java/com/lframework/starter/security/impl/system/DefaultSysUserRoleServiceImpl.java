package com.lframework.starter.security.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.security.dto.system.role.DefaultSysUserRoleDto;
import com.lframework.starter.security.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.security.entity.DefaultSysUserRole;
import com.lframework.starter.security.mappers.system.DefaultSysUserRoleMapper;
import com.lframework.starter.security.service.system.ISysRoleService;
import com.lframework.starter.security.service.system.ISysUserRoleService;
import com.lframework.starter.security.service.system.ISysUserService;
import com.lframework.starter.security.vo.system.user.SysUserRoleSettingVo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysUserRoleServiceImpl implements ISysUserRoleService {

  @Autowired
  private DefaultSysUserRoleMapper defaultSysUserRoleMapper;

  @Autowired
  private ISysUserService sysUserService;

  @Autowired
  private ISysRoleService sysRoleService;

  @OpLog(type = OpLogType.OTHER, name = "用户授权角色，用户ID：{}，角色ID：{}", params = {"#vo.userIds",
      "#vo.roleIds"}, loopFormat = true)
  @Transactional
  @Override
  public void setting(SysUserRoleSettingVo vo) {

    for (String userId : vo.getUserIds()) {
      DefaultSysUserDto user = sysUserService.getById(userId);
      if (ObjectUtil.isNull(user)) {
        throw new DefaultClientException("用户不存在！");
      }

      this.doSetting(userId, vo.getRoleIds());
    }
  }

  @Override
  public List<DefaultSysUserRoleDto> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  protected void doSetting(String userId, List<String> roleIds) {

    Wrapper<DefaultSysUserRole> deleteWrapper = Wrappers.lambdaQuery(DefaultSysUserRole.class)
        .eq(DefaultSysUserRole::getUserId, userId);
    defaultSysUserRoleMapper.delete(deleteWrapper);

    if (!CollectionUtil.isEmpty(roleIds)) {
      Set<String> roleIdSet = new HashSet<>(roleIds);

      for (String roleId : roleIdSet) {
        DefaultSysRoleDto role = sysRoleService.getById(roleId);
        if (ObjectUtil.isNull(role)) {
          throw new DefaultClientException("角色不存在，请检查！");
        }

        DefaultSysUserRole record = new DefaultSysUserRole();
        record.setId(IdUtil.getId());
        record.setUserId(userId);
        record.setRoleId(role.getId());

        defaultSysUserRoleMapper.insert(record);
      }
    }
  }

  protected List<DefaultSysUserRoleDto> doGetByUserId(String userId) {

    return defaultSysUserRoleMapper.getByUserId(userId);
  }
}
