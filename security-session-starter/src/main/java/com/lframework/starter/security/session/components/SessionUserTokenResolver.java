package com.lframework.starter.security.session.components;

import com.lframework.starter.security.components.IUserTokenResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionUserTokenResolver implements IUserTokenResolver {

  @Override
  public String getToken(HttpServletRequest request) {
    return getFullToken(request);
  }

  @Override
  public String getFullToken(HttpServletRequest request) {
    if (request == null) {
      return null;
    }

    HttpSession session = request.getSession(false);
    if (session == null) {
      return null;
    }

    return session.getId();
  }
}
