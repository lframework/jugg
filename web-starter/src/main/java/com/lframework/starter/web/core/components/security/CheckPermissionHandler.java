package com.lframework.starter.web.core.components.security;

/**
 * 权限验证
 *
 * @author zmj
 */
public interface CheckPermissionHandler {

  /**
   * 验证权限
   *
   * @param permissions
   * @return
   */
  boolean valid(PermissionCalcType calcType, String... permissions);
}
