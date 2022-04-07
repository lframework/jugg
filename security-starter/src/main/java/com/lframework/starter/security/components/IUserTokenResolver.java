package com.lframework.starter.security.components;

import javax.servlet.http.HttpServletRequest;

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
  String getToken(HttpServletRequest request);

  /**
   * 获取完整Token 与getToken的区别是：Jwt时会拼接前缀。Session时无区别
   *
   * @param request
   * @return
   */
  String getFullToken(HttpServletRequest request);
}
