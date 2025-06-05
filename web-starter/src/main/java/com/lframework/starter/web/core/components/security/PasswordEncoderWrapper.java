package com.lframework.starter.web.core.components.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 登陆密码Encoder Wrapper 用于包装Encoder 方便统一管理
 *
 * @author zmj
 */
public class PasswordEncoderWrapper {

  @Autowired
  private PasswordEncoder encoder;

  public PasswordEncoder getEncoder() {

    return encoder;
  }
}
