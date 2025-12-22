package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysNotifyGroupReceiver;
import java.util.List;

public interface SysNotifyGroupReceiverService extends BaseMpService<SysNotifyGroupReceiver> {

  /**
   * 根据组ID查询接收方ID
   *
   * @param groupId
   * @return
   */
  List<String> getReceiverIdsByGroupId(String groupId);

  /**
   * 是否存在用户组
   *
   * @param userGroupId
   * @return
   */
  Boolean hasUserGroup(String userGroupId);
}
