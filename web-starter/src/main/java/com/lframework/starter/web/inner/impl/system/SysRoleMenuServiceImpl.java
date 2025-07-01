package com.lframework.starter.web.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.security.SecurityConstants;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.components.oplog.SystemOpLogType;
import com.lframework.starter.web.inner.entity.SysMenu;
import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.inner.entity.SysRoleMenu;
import com.lframework.starter.web.inner.enums.system.SysMenuDisplay;
import com.lframework.starter.web.inner.mappers.system.SysRoleMenuMapper;
import com.lframework.starter.web.inner.service.system.SysMenuService;
import com.lframework.starter.web.inner.service.system.SysRoleMenuService;
import com.lframework.starter.web.inner.service.system.SysRoleService;
import com.lframework.starter.web.inner.vo.system.role.SysRoleMenuSettingVo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleMenuServiceImpl extends
    BaseMpServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

  @Autowired
  private SysRoleService sysRoleService;

  @Autowired
  private SysMenuService sysMenuService;

  @OpLog(type = SystemOpLogType.class, name = "角色授权菜单，角色ID：{}，菜单ID：{}", params = {
      "#vo.roleIds",
      "#vo.menuIds"}, loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysRoleMenuSettingVo vo) {

    for (String roleId : vo.getRoleIds()) {
      SysRole role = sysRoleService.findById(roleId);
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

    Wrapper<SysRoleMenu> deleteWrapper = Wrappers.lambdaQuery(SysRoleMenu.class)
        .eq(SysRoleMenu::getRoleId, roleId);
    getBaseMapper().delete(deleteWrapper);

    List<SysRoleMenu> records = new ArrayList<>();
    if (!CollectionUtil.isEmpty(menuIds)) {
      Set<String> menuIdSet = new HashSet<>(menuIds);

      // 根据所有的menuIds查询父级ID，父级ID需要添加
      for (String menuId : menuIds) {
        menuIdSet.addAll(sysMenuService.getParentMenuIds(menuId));
      }

      List<SysMenu> allMenus = menuIdSet.stream().map(sysMenuService::findById).collect(
          Collectors.toList());
      // 需要判断每一个类型是目录的菜单是否都包含菜单或权限的子菜单
      for (SysMenu menu : allMenus) {
        if (menu.getDisplay() != SysMenuDisplay.CATALOG) {
          continue;
        }

        List<String> parentIds = new ArrayList<>();
        parentIds.add(menu.getId());
        List<SysMenu> childrenMenus = new ArrayList<>();
        while (true) {
          List<SysMenu> children = allMenus.stream()
              .filter(t -> parentIds.contains(t.getParentId())).collect(
                  Collectors.toList());
          if (CollectionUtil.isEmpty(children)) {
            break;
          }
          childrenMenus.addAll(children);
          parentIds.clear();
          parentIds.addAll(children.stream().map(SysMenu::getId).collect(Collectors.toList()));
        }

        if (childrenMenus.stream().noneMatch(t -> t.getDisplay() == SysMenuDisplay.FUNCTION
            || t.getDisplay() == SysMenuDisplay.PERMISSION)) {
          throw new DefaultClientException("菜单：“" + menu.getTitle() + "”授权失败，该菜单的类型是“"
              + SysMenuDisplay.CATALOG.getDesc() + "”，必须包含子菜单！");
        }
      }
      for (String menuId : menuIdSet) {
        SysRoleMenu record = new SysRoleMenu();
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
