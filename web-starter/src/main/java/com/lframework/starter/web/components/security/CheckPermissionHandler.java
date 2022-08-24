package com.lframework.starter.web.components.security;

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
  boolean valid(String... permissions);
}
