package com.lframework.starter.websocket.components;

import com.lframework.starter.web.dto.WsPushData;

public interface WsDataPusher {

  /**
   * 推送数据
   *
   * @param data
   */
  void push(WsPushData data);
}
