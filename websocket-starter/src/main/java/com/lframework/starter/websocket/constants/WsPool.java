package com.lframework.starter.websocket.constants;

public interface WsPool {

  /**
   * WS接收的数据处理器的Bean名称后缀
   */
  String WS_RECEIVE_BEAN_SUFFIX = "WsReceiveBean";

  String TOKEN_KEY = "__token";

  String USER_KEY = "__user";

  // 自定义消息业务类型
  // 用户连接
  String PUSH_CONNECT = "connect";

  // 用户断开连接
  String PUSH_DISCONNECT = "disconnect";
}
