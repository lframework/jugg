package com.lframework.starter.web.websocket.listener;

import com.lframework.starter.web.websocket.events.UserConnectEvent;
import com.lframework.starter.web.websocket.components.WsDataPusher;
import com.lframework.starter.web.websocket.dto.WsPushData;
import com.lframework.starter.web.websocket.constants.WsPool;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

@Slf4j
public class WsUserConnectListener implements ApplicationListener<UserConnectEvent> {

  @Autowired
  private WsDataPusher wsDataPusher;

  @Override
  public void onApplicationEvent(UserConnectEvent event) {
    if (log.isDebugEnabled()) {
      log.debug("监听到用户建立连接事件，event={}", event);
    }
    WsPushData data = new WsPushData();
    data.setBizType(WsPool.PUSH_CONNECT);
    data.setAll(Boolean.TRUE);
    data.setTenantId(event.getUser().getTenantId());
    data.setExcludeSessionIds(Collections.singletonList(event.getSessionId()));

    // 这里直接用map了，不构建bo了
    Map<String, Object> map = new HashMap<>();
    map.put("name", event.getUser().getName());
    map.put("ip", event.getUser().getIp());
    map.put("createTime", LocalDateTime.now());
    data.setDataObj(map);

    wsDataPusher.push(data);
  }
}
