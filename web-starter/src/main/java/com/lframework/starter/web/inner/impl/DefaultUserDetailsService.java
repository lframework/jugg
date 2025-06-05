package com.lframework.starter.web.inner.impl;

import com.lframework.starter.web.core.components.security.AbstractUserDetailsService;
import com.lframework.starter.web.inner.mappers.UserDetailsMapper;
import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserDetaisService默认实现
 *
 * @author zmj
 */
@Service
public class DefaultUserDetailsService extends AbstractUserDetailsService {

  @Autowired
  private UserDetailsMapper userDetailsMapper;

  @Override
  public AbstractUserDetails findByUsername(String username) {

    AbstractUserDetails user = userDetailsMapper.findByUsername(username);

    return user;
  }
}
