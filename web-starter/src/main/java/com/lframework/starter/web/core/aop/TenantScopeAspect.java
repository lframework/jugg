package com.lframework.starter.web.core.aop;

import com.lframework.starter.web.core.annotations.tenant.TenantScope;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import java.lang.reflect.Method;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

/**
 * 租户上下文切面
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantScopeAspect {

  @Pointcut("(@within(com.lframework.starter.web.core.annotations.tenant.TenantScope) || @annotation(com.lframework.starter.web.core.annotations.tenant.TenantScope)) && execution(public * *(..))")
  public void tenantScopePointCut() {

  }

  @Around(value = "tenantScopePointCut()")
  public Object tenantScope(ProceedingJoinPoint joinPoint) throws Throwable {
    TenantScope tenantScope = getTenantScope(joinPoint);
    if (tenantScope == null) {
      return joinPoint.proceed();
    }

    Integer oldTenantId = TenantContextHolder.getTenantId();
    Integer tenantId = tenantScope.value();
    boolean tenantChanged = !Objects.equals(oldTenantId, tenantId);
    if (tenantChanged) {
      TenantContextHolder.setTenantId(tenantId);
    }

    try {
      return joinPoint.proceed();
    } finally {
      if (tenantChanged) {
        TenantContextHolder.clearTenantId();
        if (oldTenantId != null) {
          TenantContextHolder.setTenantId(oldTenantId);
        }
      }
    }
  }

  private TenantScope getTenantScope(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Class<?> targetClass =
        joinPoint.getTarget() == null ? method.getDeclaringClass() : joinPoint.getTarget()
            .getClass();
    Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);

    TenantScope tenantScope = AnnotationUtils.findAnnotation(specificMethod, TenantScope.class);
    if (tenantScope != null) {
      return tenantScope;
    }

    return AnnotationUtils.findAnnotation(targetClass, TenantScope.class);
  }
}
