package com.lframework.starter.security.components;

import com.lframework.starter.security.event.LoginEvent;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.SecurityUtil;
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

    String token = this.doAuthenticationSuccess(request, response, authentication);

    ApplicationUtil.publishEvent(new LoginEvent(this, SecurityUtil.getCurrentUser(), token));
  }

  /**
   * 认证成功后置处理
   *
   * @param request
   * @param response
   * @param authentication
   * @return token
   */
  protected abstract String doAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication);
}
