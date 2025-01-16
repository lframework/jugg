package com.lframework.starter.web.components.security;

import com.lframework.starter.common.exceptions.impl.UserLoginException;

public interface UserDetailsService {

  /**
   * 根据用户名查询 用于登录认证
   *
   * @param username
   * @return
   * @throws UserLoginException
   */
  AbstractUserDetails loadUserByUsername(String username) throws UserLoginException;
}
