package com.lframework.starter.security.components;

import com.lframework.common.constants.StringPool;
import com.lframework.starter.security.event.LogoutEvent;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.SecurityUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * 用户退出登录处理器
 *
 * @author zmj
 */
public class DefaultLogoutHandler implements LogoutHandler {

  private final CookieHandler cookieHandler = ApplicationUtil.getBean(CookieHandler.class);

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {

    String token = ApplicationUtil.getBean(IUserTokenResolver.class).getToken(request);

    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser(authentication);

    cookieHandler.deleteCookie(request, StringPool.HEADER_NAME_SESSION_ID);

    if (currentUser != null) {
      ApplicationUtil.publishEvent(new LogoutEvent(this, currentUser, token));
    }
  }
}
