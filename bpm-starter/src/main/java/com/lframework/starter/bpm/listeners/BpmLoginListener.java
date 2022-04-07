package com.lframework.starter.bpm.listeners;

import com.lframework.common.utils.DateUtil;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.security.event.LoginEvent;
import com.lframework.starter.security.jwt.components.JwtUserTokenResolver;
import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BpmLoginListener implements ApplicationListener<LoginEvent> {

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private JwtUserTokenResolver jwtUserTokenResolver;

  @Override
  public void onApplicationEvent(LoginEvent loginEvent) {

    String token = loginEvent.getToken();

    Claims claims = jwtUserTokenResolver.resolveClaims(token);

    redisHandler.set(String.format("jwt:%s:%s", claims.getAudience(), claims.getSubject()), token,
        Math.max(DateUtil.getTime(claims.getExpiration()) - DateUtil.getTime(LocalDateTime.now()),
            1L));
  }
}
