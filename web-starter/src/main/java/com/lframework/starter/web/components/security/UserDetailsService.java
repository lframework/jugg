package com.lframework.starter.web.components.security;

import com.lframework.starter.common.exceptions.impl.UserLoginException;
import com.lframework.starter.web.common.security.AbstractUserDetails;

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
