package com.lframework.starter.security.session.components;

import com.lframework.starter.security.components.AbstractLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class DefaultLogoutSuccessHandler extends AbstractLogoutSuccessHandler {

  @Override
  protected void doLogoutSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication authentication) {

  }
}
