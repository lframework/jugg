package com.lframework.starter.security.components;

import com.lframework.starter.security.mappers.DefaultUserDetailsMapper;
import com.lframework.starter.web.components.security.AbstractUserDetails;
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
