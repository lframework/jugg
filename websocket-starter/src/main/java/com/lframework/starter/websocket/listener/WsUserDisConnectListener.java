package com.lframework.starter.websocket.listener;

import com.lframework.starter.websocket.events.UserDisConnectEvent;
import com.lframework.starter.websocket.components.WsDataPusher;
import com.lframework.starter.web.dto.WsPushData;
import com.lframework.starter.websocket.components.WsSessionManager;
import com.lframework.starter.websocket.constants.WsPool;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

@Slf4j
public class WsUserDisConnectListener implements ApplicationListener<UserDisConnectEvent> {

  @Autowired
  private WsDataPusher wsDataPusher;

  @Override
  public void onApplicationEvent(UserDisConnectEvent event) {

    if (log.isDebugEnabled()) {
      log.debug("监听到用户断开连接事件，event={}", event);
    }
    WsPushData data = new WsPushData();
    data.setBizType(WsPool.PUSH_DISCONNECT);
    data.setAll(Boolean.TRUE);
    data.setTenantId(event.getUser().getTenantId());

    // 这里直接用map了，不构建bo了
    Map<String, Object> map = new HashMap<>();
    map.put("name", event.getUser().getName());
    map.put("ip", event.getUser().getIp());
    map.put("createTime", LocalDateTime.now());
    data.setDataObj(map);

    wsDataPusher.push(data);

    WsSessionManager.removeSession(event.getSessionId());
  }
}
