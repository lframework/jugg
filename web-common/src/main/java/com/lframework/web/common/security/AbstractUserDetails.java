package com.lframework.web.common.security;

import java.io.Serializable;
import java.util.Set;
import lombok.Data;

/**
 * 系统内UserDetails都需要继承此类
 *
 * @author zmj
 */
@Data
public abstract class AbstractUserDetails implements UserDetails, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户ID 由AbstractUserDetailsService查询
   */
  private String id;

  /**
   * 登录名 由AbstractUserDetailsService查询
   */
  private String username;

  /**
   * 姓名 由AbstractUserDetailsService查询
   */
  private String name;

  /**
   * 密码 由AbstractUserDetailsService查询
   */
  private String password;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 联系电话
   */
  private String telephone;

  /**
   * 状态 由AbstractUserDetailsService查询
   */
  private Boolean available;

  /**
   * 权限 由IMenuService查询
   */
  private Set<String> permissions;

  /**
   * 用户IP地址 会自动获取
   */
  private String ip;

  /**
   * 锁定状态
   */
  private Boolean lockStatus;

  @Override
  public boolean isAccountNonExpired() {

    return true;
  }

  @Override
  public boolean isAccountNonLocked() {

    return !this.lockStatus;
  }

  @Override
  public boolean isCredentialsNonExpired() {

    return true;
  }

  @Override
  public boolean isEnabled() {

    return this.available;
  }

  /**
   * 是否无权限
   *
   * @return
   */
  public boolean isNoPermission() {

    return this.permissions == null || this.permissions.size() == 0;
  }

  public boolean isAdmin() {

    return !isNoPermission() && this.permissions.contains(
        SecurityConstants.PERMISSION_ADMIN_NAME);
  }

}
