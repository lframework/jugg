package com.lframework.starter.security.jwt.components;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.security.components.AbstractAuthenticationSuccessHandler;
import com.lframework.starter.security.config.SessionProperties;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.dto.LoginDto;
import com.lframework.starter.web.utils.ResponseUtil;
import com.lframework.starter.web.utils.SecurityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationSuccessHandler extends AbstractAuthenticationSuccessHandler {

  @Autowired
  private SessionProperties sessionProperties;

  @Autowired
  private org.springframework.boot.autoconfigure.session.SessionProperties springSessionProperties;

  @Autowired
  private RedisHandler redisHandler;

  @Override
  protected String doAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    AbstractUserDetails user = SecurityUtil.getCurrentUser();

    LocalDateTime now = LocalDateTime.now();
    // 默认2小时
    long timeout = springSessionProperties.getTimeout() == null ? 2 * 60 * 60
        : springSessionProperties.getTimeout().getSeconds();
    LocalDateTime expireTime = now.plusSeconds(timeout);

    String token = Jwts.builder().setSubject(user.getUsername()).setIssuedAt(DateUtil.toDate(now))
        .signWith(SignatureAlgorithm.HS512, sessionProperties.getTokenSecret())
        .setExpiration(DateUtil.toDate(expireTime)).compact();

    String tokenKey = StringUtil.format(StringPool.USER_TOKEN_KEY, user.getUsername());
    redisHandler.hset(tokenKey, DateUtil.formatDateTime(expireTime), token, timeout * 1000L);

    String respToken = StringPool.TOKEN_START_WITH_STR + token;
    LoginDto dto = new LoginDto(respToken, user.getName(), user.getPermissions());

    // 达到最大用户数不允许在线时才维护自动掉线功能
    if (!sessionProperties.getMaxSessionsPreventsLogin()) {
      Map<String, Object> tokenMap = redisHandler.hgetAll(tokenKey);
      // 根据key升序
      Set<Entry<String, Object>> tokenSet = tokenMap.entrySet().stream()
          .sorted(Entry.comparingByKey()).collect(
              Collectors.toCollection(LinkedHashSet::new));
      // 允许同时在线数不能小于1
      int limitSessions = Math.max(sessionProperties.getMaximumSessions(), 1);
      int currentSize = tokenSet.size();
      while (currentSize > limitSessions) {
        // 让先登录的用户掉线
        for (Entry<String, Object> tokenEntry : tokenSet) {
          redisHandler.hdel(tokenKey, tokenEntry.getKey());
          break;
        }
        currentSize--;
      }
    }

    response.addCookie(new Cookie(StringPool.HEADER_NAME_SESSION_ID, respToken));
    ResponseUtil.respSuccessJson(response, dto);

    return token;
  }
}
