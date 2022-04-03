package com.lframework.starter.security.session.config;

import com.lframework.common.utils.CollectionUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

public class XHttpSessionIdResolver implements HttpSessionIdResolver {

  private final String sessionKey;

  private final HeaderHttpSessionIdResolver headerHttpSessionIdResolver;

  private final CookieHttpSessionIdResolver cookieHttpSessionIdResolver;

  public XHttpSessionIdResolver(String sessionKey) {
    this.sessionKey = sessionKey;

    this.headerHttpSessionIdResolver = new HeaderHttpSessionIdResolver(sessionKey);

    this.cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();

    DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
    cookieSerializer.setCookieName(sessionKey);
    cookieSerializer.setUseBase64Encoding(false);
    this.cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer);
  }

  @Override
  public List<String> resolveSessionIds(HttpServletRequest request) {

    List<String> results = this.headerHttpSessionIdResolver.resolveSessionIds(request);
    if (CollectionUtil.isEmpty(results)) {
      return this.cookieHttpSessionIdResolver.resolveSessionIds(request);
    }

    return results;
  }

  @Override
  public void setSessionId(HttpServletRequest request, HttpServletResponse response,
      String sessionId) {

    this.headerHttpSessionIdResolver.setSessionId(request, response, sessionId);

    this.cookieHttpSessionIdResolver.setSessionId(request, response, sessionId);
  }

  @Override
  public void expireSession(HttpServletRequest request, HttpServletResponse response) {

    this.headerHttpSessionIdResolver.expireSession(request, response);

    this.cookieHttpSessionIdResolver.expireSession(request, response);
  }
}
