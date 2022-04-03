package com.lframework.starter.security.components;

import com.lframework.starter.web.utils.ResponseUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 用户退出登录成功处理器
 *
 * @author zmj
 */
public abstract class AbstractLogoutSuccessHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication authentication)
      throws IOException, ServletException {

    this.doLogoutSuccess(httpServletRequest, httpServletResponse, authentication);

    ResponseUtil.respSuccessJson(httpServletResponse, null);
  }

  protected abstract void doLogoutSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication authentication);
}
