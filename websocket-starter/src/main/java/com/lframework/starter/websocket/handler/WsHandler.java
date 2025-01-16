package com.lframework.starter.websocket.handler;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.websocket.events.UserConnectEvent;
import com.lframework.starter.websocket.events.UserDisConnectEvent;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.components.tenant.TenantContextHolder;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.web.utils.TenantUtil;
import com.lframework.starter.websocket.components.WsSessionManager;
import com.lframework.starter.websocket.constants.WsPool;
import com.lframework.starter.websocket.entity.WsSession;
import com.lframework.starter.websocket.service.WsDataProcessService;
import com.lframework.starter.websocket.vo.WsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class WsHandler extends TextWebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    WsSession wsSession = WsSession.get(session);
    AbstractUserDetails currentUser = wsSession.getUser();

    try {
      if (TenantUtil.enableTenant()) {
        TenantContextHolder.setTenantId(currentUser.getTenantId());
      }

      SecurityUtil.setCurrentUser(currentUser);

      ApplicationUtil.publishEvent(new UserConnectEvent(this, session.getId(), currentUser));
    } finally {
      TenantContextHolder.clearTenantId();
      SecurityUtil.removeCurrentUser();
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    WsSession wsSession = WsSessionManager.getSession(session.getId());
    AbstractUserDetails currentUser = wsSession.getUser();

    try {
      if (TenantUtil.enableTenant()) {
        TenantContextHolder.setTenantId(currentUser.getTenantId());
      }

      SecurityUtil.setCurrentUser(currentUser);

      ApplicationUtil.publishEvent(new UserDisConnectEvent(this, session.getId(), currentUser));
    } finally {
      TenantContextHolder.clearTenantId();
      SecurityUtil.removeCurrentUser();
    }
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    WsSession wsSession = WsSessionManager.getSession(session.getId());
    AbstractUserDetails currentUser = wsSession.getUser();

    if (currentUser == null) {
      // 没有用户信息，过滤
      return;
    }

    WsVo vo = parseVo(message.getPayload());
    if (vo == null) {
      // 如果接收到空消息，过滤
      return;
    }

    if (StringUtil.isBlank(vo.getBizType())) {
      // 如果业务类型为空，过滤
      return;
    }

    try {
      if (TenantUtil.enableTenant()) {
        TenantContextHolder.setTenantId(currentUser.getTenantId());
      }

      SecurityUtil.setCurrentUser(currentUser);

      try {
        WsDataProcessService bean = (WsDataProcessService) ApplicationUtil.getBean(
            vo.getBizType() + WsPool.WS_RECEIVE_BEAN_SUFFIX);
        bean.process(vo);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return;
      }
    } finally {
      TenantContextHolder.clearTenantId();
      SecurityUtil.removeCurrentUser();
    }
  }

  private WsVo parseVo(String str) {
    if (StringUtil.isBlank(str)) {
      return null;
    }

    return JsonUtil.parseObject(str, WsVo.class);
  }
}
