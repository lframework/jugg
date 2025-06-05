package com.lframework.starter.web.websocket.service;

import com.lframework.starter.web.websocket.vo.WsVo;

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
