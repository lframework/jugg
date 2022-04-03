package com.lframework.starter.security.components;

import com.lframework.common.utils.ArrayUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieHandler {

  /**
   * 删除cookie
   *
   * @param request
   * @param name
   */
  public void deleteCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (!ArrayUtil.isEmpty(cookies)) {
      for (Cookie cookie : cookies) {
        if (name.equals(cookie.getName())) {
          cookie.setMaxAge(0);
        }
      }
    }
  }
}
