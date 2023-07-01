package com.lframework.starter.websocket.components;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.web.dto.WsPushData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;

@Slf4j
public class WsDataPushWorker {

  public static void push(WsPushData data) {
    if (log.isDebugEnabled()) {
      log.debug("开始推送消息，data = {}", data);
    }
    if (data == null) {
      return;
    }

    if (data.getAll()) {
      if (log.isDebugEnabled()) {
        log.debug("全员广播");
      }
      // 全员广播
      WsSessionManager.getAllAvailableSessions(data.getTenantId(), data.getIncludeSessionIds(), data.getExcludeSessionIds())
          .stream().forEach(t -> {
            try {
              Map<String, Object> obj = new HashMap(2, 1);
              obj.put("bizType", data.getBizType());
              obj.put("data", data.getData());
              t.getWebSocketSession().sendMessage(new TextMessage(JsonUtil.toJsonString(obj)));
            } catch (IOException e) {
              log.error(e.getMessage(), e);
            }
          });
    } else {
      List<String> userIds = new ArrayList<>();
      if (CollectionUtil.isNotEmpty(data.getIncludeUserIds())) {
        userIds.addAll(data.getIncludeUserIds());
      }

      if (StringUtil.isNotBlank(data.getIncludeUserId())) {
        userIds.add(data.getIncludeUserId());
      }

      if (log.isDebugEnabled()) {
        log.debug("部分成员接收消息");
      }

      WsSessionManager.getAvailableSessionsByUserIds(data.getTenantId(), userIds, data.getIncludeSessionIds(),
          data.getExcludeSessionIds()).forEach(t -> {
        try {
          Map<String, Object> obj = new HashMap(2, 1);
          obj.put("bizType", data.getBizType());
          obj.put("data", data.getData());
          t.getWebSocketSession().sendMessage(new TextMessage(JsonUtil.toJsonString(obj)));
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      });
    }
  }
}
