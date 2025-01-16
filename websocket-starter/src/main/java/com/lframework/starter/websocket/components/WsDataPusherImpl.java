package com.lframework.starter.websocket.components;

import com.lframework.starter.web.components.tenant.TenantContextHolder;
import com.lframework.starter.web.dto.WsPushData;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.web.utils.TenantUtil;
import com.lframework.starter.websocket.config.WsProperties;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class WsDataPusherImpl implements WsDataPusher {

  private RedisTemplate redisTemplate;

  private WsProperties properties;

  public WsDataPusherImpl(RedisTemplate redisTemplate, WsProperties properties) {
    this.redisTemplate = redisTemplate;
    this.properties = properties;
  }

  public void push(WsPushData data) {
    if (TenantUtil.enableTenant()) {
      if (data.getTenantId() == null) {
        data.setTenantId(TenantContextHolder.getTenantId());
      }
    }
    if (log.isDebugEnabled()) {
      log.debug("准备推送消息，data = {}", data);
    }
    List<String> parameters = new ArrayList<>();
    parameters.add(properties.getTopic());
    parameters.add(JsonUtil.toJsonString(data));
    this.redisTemplate.execute(connection -> {
      Object result;
      byte[][] params = new byte[parameters.size()][];
      for (int i = 0; i < params.length; i++) {
        params[i] = serializer(parameters.get(i));
      }
      result = connection.execute("publish", params);
      return deserialize(result);
    }, this.redisTemplate.isExposeConnection());
  }

  private byte[] serializer(Object value) {
    if (value == null || value instanceof String) {
      return redisTemplate.getStringSerializer().serialize((String) value);
    }
    return serializer(value.toString());
  }

  private Object deserialize(Object value) {
    if (value != null) {
      if (value instanceof byte[]) {
        return this.redisTemplate.getStringSerializer().deserialize((byte[]) value);
      }
      if (value instanceof List) {
        List<Object> valueList = (List<Object>) value;
        List<Object> resultList = new ArrayList<>(valueList.size());
        for (Object val : valueList) {
          resultList.add(deserialize(val));
        }
        return resultList;
      }
      if (value instanceof Map) {
        Map<Object, Object> map = (Map<Object, Object>) value;
        LinkedHashMap<Object, Object> newMap = new LinkedHashMap<>(map.size());
        map.forEach((key, val) -> newMap.put(deserialize(key), deserialize(val)));
        return newMap;
      }
    }
    return value;
  }
}
