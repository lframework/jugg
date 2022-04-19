package com.lframework.starter.web.components.security;

/**
 * 用户Token解析器
 */
public interface IUserTokenResolver {

  /**
   * 获取Token
   *
   * @param request
   * @return 如果没有token，则返回null
   */
  String getToken();

  /**
   * 获取完整Token 与getToken的区别是：Jwt时会拼接前缀。Session时无区别
   *
   * @param request
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
