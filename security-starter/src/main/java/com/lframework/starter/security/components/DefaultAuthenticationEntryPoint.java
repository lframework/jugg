package com.lframework.starter.security.components;

import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.AccessDeniedException;
import com.lframework.common.exceptions.impl.AuthExpiredException;
import com.lframework.starter.web.utils.ResponseUtil;
import com.lframework.starter.web.utils.SecurityUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 用户认证失败切入点
 *
 * @author zmj
 */
@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Autowired
  private CookieHandler cookieHandler;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    if (SecurityUtil.getAuthentication() == null) {
      cookieHandler.deleteCookie(request, StringPool.HEADER_NAME_SESSION_ID);

      ResponseUtil.respFailJson(response, new AuthExpiredException());
    } else {
      ResponseUtil.respFailJson(response, new AccessDeniedException());
    }
  }
}
