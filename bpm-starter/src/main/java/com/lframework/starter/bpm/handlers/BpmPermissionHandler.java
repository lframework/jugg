package com.lframework.starter.bpm.handlers;

import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.inner.entity.SysUserDept;
import com.lframework.starter.web.inner.entity.SysUserRole;
import com.lframework.starter.web.inner.service.system.SysUserDeptService;
import com.lframework.starter.web.inner.service.system.SysUserRoleService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.dromara.warm.flow.core.handler.PermissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BpmPermissionHandler implements PermissionHandler {

  @Autowired
  private SysUserRoleService sysUserRoleService;

  @Autowired
  private SysUserDeptService sysUserDeptService;

  @Override
  public List<String> permissions() {
    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();
    List<SysUserRole> userRoleList = sysUserRoleService.getByUserId(currentUser.getId());

    List<SysUserDept> userDeptList = sysUserDeptService.getByUserId(currentUser.getId());

    List<String> permissions = new ArrayList<>();
    permissions.add(currentUser.getId());
    permissions.addAll(
        userRoleList.stream().map(t -> "role:" + t.getRoleId()).collect(Collectors.toSet()));
    permissions.addAll(
        userDeptList.stream().map(t -> "dept:" + t.getDeptId()).collect(Collectors.toSet()));

    return permissions;
  }

  @Override
  public String getHandler() {
    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();
    return currentUser == null ? null : currentUser.getId();
  }

  @Override
  public List<String> convertPermissions(List<String> permissions) {

    List<String> results = new ArrayList<>();
    for (String permission : permissions) {
      if (permission.startsWith("role:")) {
        String tmpId = permission.substring(5);
        List<SysUserRole> userRoleList = sysUserRoleService.getByRoleId(tmpId);
        results.addAll(
            userRoleList.stream().map(SysUserRole::getUserId).collect(Collectors.toSet()));
      } else if (permission.startsWith("dept:")) {
        String tmpId = permission.substring(5);
        List<SysUserDept> userDeptList = sysUserDeptService.getByDeptId(tmpId);
        results.addAll(
            userDeptList.stream().map(SysUserDept::getUserId).collect(Collectors.toSet()));
      } else {
        results.add(permission);
      }
    }
    return results;
  }
}
