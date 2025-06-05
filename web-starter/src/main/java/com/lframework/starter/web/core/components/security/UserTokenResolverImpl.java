package com.lframework.starter.web.core.components.security;

import cn.dev33.satoken.stp.StpUtil;

public class UserTokenResolverImpl implements UserTokenResolver {

  private String tokenKey = "X-Auth-Token";

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
