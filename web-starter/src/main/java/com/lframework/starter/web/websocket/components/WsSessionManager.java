package com.lframework.starter.web.websocket.components;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.utils.TenantUtil;
import com.lframework.starter.web.websocket.entity.WsSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.ehcache.impl.internal.concurrent.ConcurrentHashMap;

public class WsSessionManager {

  private static final Map<String, WsSession> POOL;

  static {
    POOL = new ConcurrentHashMap<>();
  }

  public static WsSession getSession(String id) {
    return POOL.get(id);
  }

  public static void addSession(WsSession session) {
    if (session == null) {
      throw new DefaultSysException("session is null");
    }

    if (session.getWebSocketSession() == null) {
      throw new DefaultSysException("webSocketSession is null");
    }

    POOL.put(session.getWebSocketSession().getId(), session);
  }

  public static void removeSession(String id) {
    POOL.remove(id);
  }

  public static List<WsSession> getAllAvailableSessions(Integer tenantId,
      List<String> includeSessionIds, List<String> excludeSessionIds) {
    boolean enableTenant = TenantUtil.enableTenant();
    if (enableTenant) {
      if (tenantId == null) {
        return Collections.emptyList();
      }
    }

    return POOL.values().stream()
        .filter(t -> t.getWebSocketSession() != null && t.getWebSocketSession().isOpen()
            && t.getUser() != null && (!enableTenant || tenantId.equals(t.getUser().getTenantId())))
        .filter(t -> CollectionUtil.isEmpty(includeSessionIds) || includeSessionIds.contains(
            t.getWebSocketSession().getId()))
        .filter(t -> CollectionUtil.isEmpty(excludeSessionIds) || !excludeSessionIds.contains(
            t.getWebSocketSession().getId()))
        .collect(Collectors.toList());
  }

  public static List<WsSession> getAvailableSessionsByUserIds(Integer tenantId,
      List<String> userIds, List<String> includeSessionIds, List<String> excludeSessionIds) {

    boolean enableTenant = TenantUtil.enableTenant();
    if (enableTenant) {
      if (tenantId == null) {
        return Collections.emptyList();
      }
    }

    return POOL.values().stream()
        .filter(t -> t.getWebSocketSession() != null && t.getWebSocketSession().isOpen()
            && t.getUser() != null && (CollectionUtil.isEmpty(userIds) || userIds.contains(
            t.getUser().getId())) && (!enableTenant
            || tenantId.equals(t.getUser().getTenantId())))
        .filter(t -> CollectionUtil.isEmpty(includeSessionIds) || includeSessionIds.contains(
            t.getWebSocketSession().getId()))
        .filter(t -> CollectionUtil.isEmpty(excludeSessionIds) || !excludeSessionIds.contains(
            t.getWebSocketSession().getId()))
        .collect(Collectors.toList());
  }
}
