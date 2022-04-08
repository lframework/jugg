package com.lframework.starter.bpm.listeners;

import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.security.event.LogoutEvent;
import com.lframework.starter.security.jwt.components.JwtUserTokenResolver;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BpmLogoutListener implements ApplicationListener<LogoutEvent> {

  @Autowired
  private JwtUserTokenResolver jwtUserTokenResolver;

  @Autowired
  private RedisHandler redisHandler;

  @Override
  public void onApplicationEvent(LogoutEvent logoutEvent) {
    String token = logoutEvent.getToken();

    Claims claims = jwtUserTokenResolver.resolveClaims(token);

    redisHandler.del(String.format("jwt:%s:%s", claims.getAudience(), claims.getSubject()));
  }
}
