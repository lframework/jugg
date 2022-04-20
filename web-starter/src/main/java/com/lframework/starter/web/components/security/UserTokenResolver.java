package com.lframework.starter.web.components.security;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserTokenResolver implements IUserTokenResolver {

  @Value("${session.token-key:'X-Auth-Token'}")
  private String tokenKey;

  @Override
  public String getToken() {

    return StpUtil.getTokenValue();
  }

  @Override
  public String getFullToken() {

    return getToken();
  }

  @Override
  public String getTokenKey() {

    return tokenKey;
  }
}
