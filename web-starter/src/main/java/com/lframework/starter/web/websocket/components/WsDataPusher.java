package com.lframework.starter.web.websocket.components;

import com.lframework.starter.web.websocket.dto.WsPushData;

public interface WsDataPusher {

  /**
   * 推送数据
   *
   * @param data
   */
  void push(WsPushData data);
}
