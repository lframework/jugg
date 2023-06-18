package com.lframework.starter.web.common.security;

import java.io.Serializable;

public interface UserDetails extends Serializable {

  /**
   * 用户密码
   *
   * @return
   */
  String getPassword();

  /**
   * 用户名
   *
   * @return
   */
  String getUsername();

  /**
   * 用户是否未过期
   *
   * @return
   */
  boolean isAccountNonExpired();

  /**
   * 用户是否未锁定
   *
   * @return
   */
  boolean isAccountNonLocked();

  /**
   * 密码是否未过期
   *
   * @return
   */
  boolean isCredentialsNonExpired();

  /**
   * 用户是否可用
   *
   * @return
   */
  boolean isEnabled();

  /**
   * 是否管理员 这里指的是管理员身份，可以获取全部菜单
   *
   * @return
   */
  boolean isAdmin();

  /**
   * 是否有管理员权限 这里值得是管理员具体权限，因为管理员权限会通过权限校验
   *
   * @return
   */
  boolean hasAdminPermission();
}
