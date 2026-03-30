package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysUserMenuSort;
import java.util.List;

/**
 * 用户菜单排序偏好 Service
 *
 * @author lframework@163.com
 */
public interface SysUserMenuSortService extends BaseMpService<SysUserMenuSort> {

  /**
   * 查询用户菜单排序偏好
   */
  List<SysUserMenuSort> getByUserId(String userId);

  /**
   * 全量覆盖保存用户菜单排序偏好
   */
  void replaceUserSorts(String userId, List<SysUserMenuSort> records);
}
