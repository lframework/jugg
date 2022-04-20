package com.lframework.starter.security.components;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.lframework.common.exceptions.impl.AuthExpiredException;
import com.lframework.starter.web.components.security.PermitAllService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

  private PermitAllService permitAllService;

  public LoginInterceptor(PermitAllService permitAllService) {

    this.permitAllService = permitAllService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (permitAllService.isMatch(request)) {
      log.debug("uri={}，无需登录验证", request.getRequestURI());
      return true;
    }

    try {
      StpUtil.checkLogin();
    } catch (NotLoginException e) {
      throw new AuthExpiredException();
    }

    return true;
  }
}
