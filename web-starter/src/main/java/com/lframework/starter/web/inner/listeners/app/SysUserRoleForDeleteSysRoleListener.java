package com.lframework.starter.web.inner.listeners.app;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.inner.entity.SysUserRole;
import com.lframework.starter.web.inner.events.system.DeleteSysRoleEvent;
import com.lframework.starter.web.inner.service.system.SysUserRoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SysUserRoleForDeleteSysRoleListener implements
    ApplicationListener<DeleteSysRoleEvent> {

  @Autowired
  private SysUserRoleService sysUserRoleService;

  @Override
  public void onApplicationEvent(DeleteSysRoleEvent event) {
    String roleId = event.getEntity().getId();
    String roleName = event.getEntity().getName();
    // 判断是否有用户关联了该角色
    List<SysUserRole> result = sysUserRoleService.getByRoleId(roleId);
    if (CollectionUtil.isNotEmpty(result)) {
      throw new DefaultClientException("角色：" + roleName + "下存在用户，不允许删除！");
    }
  }
}
