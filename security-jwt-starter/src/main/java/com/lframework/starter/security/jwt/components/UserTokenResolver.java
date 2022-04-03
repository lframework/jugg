package com.lframework.starter.security.jwt.components;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.ArrayUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.security.config.SessionProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户Token解析器
 */
@Slf4j
@Component
public class UserTokenResolver {

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private SessionProperties sessionProperties;

  /**
   * 从request中解析Token文本
   *
   * @param request
   * @return
   */
  public String resolve(HttpServletRequest request) {
    String header = StringPool.HEADER_NAME_SESSION_ID;
    // 先尝试从Header中获取token
    String token = request.getHeader(header);
    if (StringUtil.isBlank(token)) {
      // 再尝试从Cookie中获取token
      token = this.getCookieValue(request, header);
    }

    if (!StringUtil.isBlank(token)) {
      if (token.startsWith(StringPool.TOKEN_START_WITH_STR)) {
        token = token.substring(StringPool.TOKEN_START_WITH_STR.length());
      }
    }

    return token;
  }

  public Claims resolveClaims(String token) {
    try {

      return Jwts.parser().setSigningKey(sessionProperties.getTokenSecret())
          .parseClaimsJws(token).getBody();
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
      return null;
    }
  }

  /**
   * 根据Token文本解析Token内容
   *
   * @param token
   * @return 用户ID 返回null表示token无效
   */
  public String resolveToken(String token) {
    if (StringUtil.isBlank(token)) {
      if (log.isDebugEnabled()) {
        log.debug("Token为空，无法解析");
      }
      return null;
    }

    Claims claims = this.resolveClaims(token);

    if (claims != null) {
      String oriToken = (String) redisHandler.hget(
          StringUtil.format(StringPool.USER_TOKEN_KEY, claims.getSubject()),
          DateUtil.formatDateTime(claims.getExpiration()));
      if (StringUtil.isBlank(oriToken)) {
        if (log.isDebugEnabled()) {
          log.debug("Token已过期，无法解析");
        }
        return null;
      }

      if (!oriToken.equals(token)) {
        if (log.isDebugEnabled()) {
          log.debug("Token信息有误，无法解析");
        }

        return null;
      }

      return claims.getSubject();
    } else {
      return null;
    }
  }

  /**
   * 从Cookie中读取value
   *
   * @param request
   * @param key
   * @return
   */
  private String getCookieValue(HttpServletRequest request, String key) {
    Cookie[] cookies = request.getCookies();
    if (ArrayUtil.isEmpty(cookies)) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(key)) {
        return cookie.getValue();
      }
    }

    return null;
  }
}
