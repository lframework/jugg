package com.lframework.starter.web.inner.listeners.app;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.inner.events.system.DeleteSysUserGroupEvent;
import com.lframework.starter.web.inner.service.system.SysNotifyGroupReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SysNotifyGroupForDeleteSysUserGroupListener implements
    ApplicationListener<DeleteSysUserGroupEvent> {

  @Autowired
  private SysNotifyGroupReceiverService sysNotifyGroupReceiverService;

  @Override
  public void onApplicationEvent(DeleteSysUserGroupEvent event) {
    if (sysNotifyGroupReceiverService.hasUserGroup(event.getEntity().getId())) {
      throw new DefaultClientException(
          "用户分组：" + event.getEntity().getName() + "存在关联的消息通知组，请先解除关联关系！");
    }
  }
}
