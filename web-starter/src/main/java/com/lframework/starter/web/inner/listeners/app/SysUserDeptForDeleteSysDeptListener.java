package com.lframework.starter.web.inner.listeners.app;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.inner.events.system.DeleteSysDeptEvent;
import com.lframework.starter.web.inner.service.system.SysUserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SysUserDeptForDeleteSysDeptListener implements
    ApplicationListener<DeleteSysDeptEvent> {

  @Autowired
  private SysUserDeptService sysUserDeptService;

  @Override
  public void onApplicationEvent(DeleteSysDeptEvent event) {
    String deptId = event.getEntity().getId();
    String deptName = event.getEntity().getName();
    // 判断是否有用户关联了该部门
    Boolean result = sysUserDeptService.hasByDeptId(deptId);
    if (result) {
      throw new DefaultClientException("部门：" + deptName + "下存在用户，不允许删除！");
    }
  }
}
