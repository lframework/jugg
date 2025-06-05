package com.lframework.starter.web.websocket.interceptor;

import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.websocket.constants.WsPool;
import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class WsHandshakeInterceptor implements HandshakeInterceptor {

  @Override
  public boolean beforeHandshake(ServerHttpRequest request,
      ServerHttpResponse response, WebSocketHandler wsHandler,
      Map<String, Object> attributes) throws Exception {

    String token = ((ServletServerHttpRequest) request).getServletRequest()
        .getParameter("X-Auth-Token");

    AbstractUserDetails currentUser = SecurityUtil.getUserByToken(token);
    if (currentUser == null) {
      return false;
    }

    attributes.put(WsPool.TOKEN_KEY, token);
    attributes.put(WsPool.USER_KEY, currentUser);
    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request,
      ServerHttpResponse response, WebSocketHandler wsHandler, Exception e) {
  }
}
