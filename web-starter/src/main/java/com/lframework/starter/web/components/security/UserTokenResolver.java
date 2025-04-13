package com.lframework.starter.web.components.security;

import cn.dev33.satoken.stp.StpUtil;

public class UserTokenResolver implements IUserTokenResolver {

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
