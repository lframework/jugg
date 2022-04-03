package com.lframework.starter.security.components;

import com.lframework.starter.security.event.LoginEvent;
import com.lframework.starter.web.utils.ApplicationUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 用户认证成功处理器
 *
 * @author zmj
 */
public abstract class AbstractAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    this.doAuthenticationSuccess(request, response, authentication);

    ApplicationUtil.publishEvent(new LoginEvent(this));
  }

  protected abstract void doAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication);
}
