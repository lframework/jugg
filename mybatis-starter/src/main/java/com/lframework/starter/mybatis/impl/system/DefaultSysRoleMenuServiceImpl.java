package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.mybatis.entity.DefaultSysRole;
import com.lframework.starter.mybatis.entity.DefaultSysRoleMenu;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysRoleMenuMapper;
import com.lframework.starter.mybatis.service.system.SysMenuService;
import com.lframework.starter.mybatis.service.system.SysRoleMenuService;
import com.lframework.starter.mybatis.service.system.SysRoleService;
import com.lframework.starter.mybatis.vo.system.role.SysRoleMenuSettingVo;
import com.lframework.starter.web.common.security.SecurityConstants;
import com.lframework.starter.web.utils.IdUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysRoleMenuServiceImpl extends
    BaseMpServiceImpl<DefaultSysRoleMenuMapper, DefaultSysRoleMenu>
    implements SysRoleMenuService {

  @Autowired
  private SysRoleService sysRoleService;

  @Autowired
  private SysMenuService sysMenuService;

  @OpLog(type = DefaultOpLogType.OTHER, name = "角色授权菜单，角色ID：{}，菜单ID：{}", params = {"#vo.roleIds",
      "#vo.menuIds"}, loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysRoleMenuSettingVo vo) {

    for (String roleId : vo.getRoleIds()) {
      DefaultSysRole role = sysRoleService.findById(roleId);
      if (ObjectUtil.isNull(role)) {
        throw new DefaultClientException("角色不存在！");
      }

      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(role.getPermission())) {
        throw new DefaultClientException(
            "角色【" + role.getName() + "】的权限为【" + SecurityConstants.PERMISSION_ADMIN_NAME
                + "】，不允许授权！");
      }

      this.doSetting(roleId, vo.getMenuIds());
    }
  }

  protected void doSetting(String roleId, List<String> menuIds) {

    Wrapper<DefaultSysRoleMenu> deleteWrapper = Wrappers.lambdaQuery(DefaultSysRoleMenu.class)
        .eq(DefaultSysRoleMenu::getRoleId, roleId);
    getBaseMapper().delete(deleteWrapper);

    List<DefaultSysRoleMenu> records = new ArrayList<>();
    if (!CollectionUtil.isEmpty(menuIds)) {
      Set<String> menuIdSet = new HashSet<>(menuIds);

      for (String menuId : menuIdSet) {
        DefaultSysRoleMenu record = new DefaultSysRoleMenu();
        record.setId(IdUtil.getId());
        record.setRoleId(roleId);
        record.setMenuId(menuId);

        records.add(record);
      }
    }

    if (CollectionUtil.isNotEmpty(records)) {
      this.saveBatch(records);
    }
  }
}
