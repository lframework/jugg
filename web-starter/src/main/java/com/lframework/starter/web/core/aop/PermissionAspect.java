package com.lframework.starter.web.core.aop;

import com.lframework.starter.common.exceptions.impl.AccessDeniedException;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.security.CheckPermissionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 鉴权切面
 *
 * @author zmj
 */
@Aspect
public class PermissionAspect {

  @Autowired
  private CheckPermissionHandler checkPermissionHandler;

  @Pointcut("@annotation(com.lframework.starter.web.core.annotations.security.HasPermission) && execution(public * *(..))")
  public void permissionPointCut() {

  }

  @Around(value = "permissionPointCut()")
  public Object permission(ProceedingJoinPoint joinPoint) throws Throwable {

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();

    HasPermission hasPermission = signature.getMethod().getAnnotation(HasPermission.class);

    if (!checkPermissionHandler.valid(hasPermission.calcType(), hasPermission.value())) {
      throw new AccessDeniedException("暂无权限，请联系系统管理员！");
    }

    Object value = joinPoint.proceed();

    return value;
  }
}
