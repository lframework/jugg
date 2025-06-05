package com.lframework.starter.web.core.aop;

import com.lframework.starter.common.exceptions.impl.AccessDeniedException;
import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.openapi.OpenApi;
import com.lframework.starter.web.core.components.sign.CheckSignFactory;
import com.lframework.starter.web.core.components.sign.CheckSignHandler;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.vo.openapi.OpenApiReqVo;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OpenApi切面
 *
 * @author zmj
 */
@Aspect
public class OpenApiAspect {

  @Pointcut("(@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)) && @annotation(com.lframework.starter.web.core.annotations.openapi.OpenApi) && execution(public * *(..))")
  public void openApiPointCut() {

  }

  @Around(value = "openApiPointCut()")
  public Object openApi(ProceedingJoinPoint joinPoint) throws Throwable {

    Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();

    OpenApi openApi = signature.getMethod().getAnnotation(OpenApi.class);

    Object[] args = joinPoint.getArgs();

    CheckSignFactory checkSignFactory = ApplicationUtil.getBean(CheckSignFactory.class);
    CheckSignHandler checkSignHandler = checkSignFactory.getInstance();

    if (openApi.sign()) {
      if (ArrayUtil.isEmpty(args)) {
        throw new AccessDeniedException("验签失败！");
      }
      List<OpenApiReqVo> reqList = Arrays.stream(args).filter(t -> t instanceof OpenApiReqVo)
          .map(t -> (OpenApiReqVo) t).collect(Collectors.toList());

      if (CollectionUtil.isEmpty(reqList)) {
        throw new AccessDeniedException("验签失败！");
      }
      for (OpenApiReqVo req : reqList) {
        if (!checkSignHandler.check(req)) {
          throw new AccessDeniedException("验签失败！");
        }

        checkSignHandler.setTenantId(req);
      }
    }

    // 如果openApi.sign() 为false，那么需要接口自己设置TenantId

    Object value = joinPoint.proceed();

    return value;
  }
}
