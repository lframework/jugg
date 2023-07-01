package com.lframework.starter.websocket.service;

import com.lframework.starter.websocket.vo.WsVo;

/**
 * WS处理数据Service
 */
public interface WsDataProcessService {

  /**
   * 处理
   *
   * @param vo
   */
  void process(WsVo vo);
}
