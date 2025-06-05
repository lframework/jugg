package com.lframework.starter.web.core.components.security;

/**
 * 用户Token解析器
 */
public interface UserTokenResolver {

  /**
   * 获取Token
   *
   * @return 如果没有token，则返回null
   */
  String getToken();

  /**
   * 获取完整Token 与getToken的区别是：Jwt时会拼接前缀。Session时无区别
   *
   * @return
   */
  String getFullToken();

  /**
   * 获取Token的key
   *
   * @return
   */
  String getTokenKey();
}
