package com.lframework.starter.web.websocket.entity;

import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import com.lframework.starter.web.websocket.components.WsSessionManager;
import com.lframework.starter.web.websocket.constants.WsPool;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class WsSession {

  private String token;

  private WebSocketSession webSocketSession;

  private AbstractUserDetails user;


  public static WsSession get(WebSocketSession webSocketSession) {
    WsSession wsSession = WsSessionManager.getSession(webSocketSession.getId());
    if (wsSession == null) {
      wsSession = new WsSession();
      wsSession.setWebSocketSession(webSocketSession);
      wsSession.setToken(String.valueOf(webSocketSession.getAttributes().get(WsPool.TOKEN_KEY)));

      AbstractUserDetails currentUser = (AbstractUserDetails) webSocketSession.getAttributes().get(WsPool.USER_KEY);
      wsSession.setUser(currentUser);
      WsSessionManager.addSession(wsSession);
    }

    return wsSession;
  }
}
