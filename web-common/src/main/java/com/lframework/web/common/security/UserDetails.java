package com.lframework.web.common.security;

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
   * 是否管理员
   *
   * @return
   */
  boolean isAdmin();
}
