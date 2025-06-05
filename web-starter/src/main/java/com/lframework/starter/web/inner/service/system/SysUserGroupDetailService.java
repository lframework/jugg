package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysUserGroupDetail;
import java.util.List;

public interface SysUserGroupDetailService extends BaseMpService<SysUserGroupDetail> {

  /**
   * 根据组ID查询用户ID
   *
   * @param groupId
   * @return
   */
  List<String> getUserIdsByGroupId(String groupId);
}
