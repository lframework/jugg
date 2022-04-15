package com.lframework.starter.mybatis.impl;

import com.lframework.starter.mybatis.mappers.DefaultUserDetailsMapper;
import com.lframework.web.common.security.AbstractUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * UserDetaisService默认实现
 *
 * @author zmj
 */
public class DefaultUserDetailsService extends AbstractUserDetailsService {

  @Autowired
  private DefaultUserDetailsMapper defaultUserDetailsMapper;

  @Override
  public AbstractUserDetails findByUsername(String username) {

    AbstractUserDetails user = defaultUserDetailsMapper.findByUsername(username);

    return user;
  }
}
