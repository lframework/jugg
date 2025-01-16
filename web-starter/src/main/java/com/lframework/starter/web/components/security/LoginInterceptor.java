package com.lframework.starter.web.components.security;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.annotation.AnnotationUtil;
import com.lframework.starter.common.exceptions.impl.AuthExpiredException;
import com.lframework.starter.web.annotations.OpenApi;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
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

    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      OpenApi openApi = null;
      if (handlerMethod.hasMethodAnnotation(OpenApi.class)) {
        openApi = handlerMethod.getMethodAnnotation(OpenApi.class);
      } else if (AnnotationUtil.hasAnnotation(handlerMethod.getBeanType(), OpenApi.class)) {
        openApi = AnnotationUtil.getAnnotation(handlerMethod.getBeanType(), OpenApi.class);
      }
      if (openApi != null) {
        log.debug("uri={}，无需登录验证", request.getRequestURI());
        permitAllService.addMatch(request);
        return true;
      }
    }

    try {
      StpUtil.checkLogin();
    } catch (NotLoginException e) {
      throw new AuthExpiredException();
    }

    return true;
  }
}
