package com.lframework.starter.web.core.interceptors;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.lframework.starter.common.exceptions.impl.AuthExpiredException;
import com.lframework.starter.web.core.annotations.openapi.OpenApi;
import com.lframework.starter.web.core.components.security.PermitAllService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

  private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

  private final PermitAllService permitAllService;

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

    if (handler instanceof HandlerMethod handlerMethod && hasOpenApi(handlerMethod)) {
      log.debug("uri={}，无需登录验证", request.getRequestURI());
      permitAllService.addMatch(request);
      return true;
    }

    try {
      StpUtil.checkLogin();
    } catch (NotLoginException e) {
      log.debug("uri={}，登录已过期", request.getRequestURI());
      throw new AuthExpiredException();
    }

    return true;
  }

  private boolean hasOpenApi(HandlerMethod handlerMethod) {
    Method method = handlerMethod.getMethod();
    if (hasOpenApi(method)) {
      return true;
    }

    Class<?> userClass = ClassUtils.getUserClass(handlerMethod.getBeanType());
    Method userMethod = ClassUtils.getMostSpecificMethod(method, userClass);
    if (hasOpenApi(userMethod)) {
      return true;
    }

    userMethod = ReflectionUtils.findMethod(userClass, method.getName(), method.getParameterTypes());
    if (hasOpenApi(userMethod)) {
      return true;
    }

    return AnnotatedElementUtils.findMergedAnnotation(userClass, OpenApi.class) != null;
  }

  private boolean hasOpenApi(Method method) {
    if (method == null) {
      return false;
    }

    Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
    return AnnotatedElementUtils.findMergedAnnotation(bridgedMethod, OpenApi.class) != null;
  }
}
