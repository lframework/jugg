package com.lframework.starter.web.aop;

import com.lframework.starter.common.exceptions.impl.AccessDeniedException;
import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.OpenApi;
import com.lframework.starter.web.sign.CheckSignFactory;
import com.lframework.starter.web.sign.CheckSignHandler;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.vo.OpenApiReqVo;
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
import org.springframework.stereotype.Component;

/**
 * OpenApi切面
 *
 * @author zmj
 */
@Aspect
@Component
public class OpenApiAspect {

  @Pointcut("(@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)) && @annotation(com.lframework.starter.web.annotations.OpenApi) && execution(public * *(..))")
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
