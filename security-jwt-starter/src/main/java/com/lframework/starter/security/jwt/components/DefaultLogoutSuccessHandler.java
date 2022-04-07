package com.lframework.starter.security.jwt.components;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.security.components.AbstractLogoutSuccessHandler;
import com.lframework.starter.security.components.CookieHandler;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class DefaultLogoutSuccessHandler extends AbstractLogoutSuccessHandler {

  @Autowired
  private JwtUserTokenResolver jwtUserTokenResolver;

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private CookieHandler cookieHandler;

  @Override
  protected void doLogoutSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication authentication) {

    AbstractUserDetails user = (AbstractUserDetails) authentication.getPrincipal();
    String token = jwtUserTokenResolver.getToken(httpServletRequest);
    Claims claims = jwtUserTokenResolver.resolveClaims(token);

    if (user != null && !StringUtil.isBlank(token)) {
      String tokenKey = StringUtil.format(StringPool.USER_TOKEN_KEY, user.getId());
      redisHandler.hdel(tokenKey, DateUtil.formatDateTime(claims.getExpiration()));
    }
  }
}
