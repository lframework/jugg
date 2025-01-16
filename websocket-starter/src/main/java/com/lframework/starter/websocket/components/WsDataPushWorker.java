package com.lframework.starter.websocket.components;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.dto.WsPushData;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.websocket.entity.WsSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

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
      WsSessionManager.getAllAvailableSessions(data.getTenantId(), data.getIncludeSessionIds(),
              data.getExcludeSessionIds())
          .stream().forEach(t -> {
            Map<String, Object> obj = new HashMap(2, 1);
            obj.put("bizType", data.getBizType());
            obj.put("data", data.getData());
            sendMessage(t, new TextMessage(JsonUtil.toJsonString(obj)));
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

      WsSessionManager.getAvailableSessionsByUserIds(data.getTenantId(), userIds,
          data.getIncludeSessionIds(),
          data.getExcludeSessionIds()).forEach(t -> {
        Map<String, Object> obj = new HashMap(2, 1);
        obj.put("bizType", data.getBizType());
        obj.put("data", data.getData());
        sendMessage(t, new TextMessage(JsonUtil.toJsonString(obj)));
      });
    }
  }

  private static void sendMessage(WsSession session, WebSocketMessage<?> message) {
    int retryCount = 5;
    int currentRetryCount = retryCount;
    while (currentRetryCount > 0) {
      try {
        session.getWebSocketSession().sendMessage(message);
        break;
      } catch (IllegalStateException e) {
        currentRetryCount--;
        if (currentRetryCount > 0) {
          log.warn("推送WS消息失败，休眠一秒，重试第{}次", (retryCount - currentRetryCount));
        } else {
          log.error(e.getMessage(), e);
        }
        // 休眠200毫秒再重试
        ThreadUtil.safeSleep(1000);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
    }
  }
}
