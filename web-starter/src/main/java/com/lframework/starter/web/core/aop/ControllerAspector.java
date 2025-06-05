package com.lframework.starter.web.core.aop;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller切面
 *
 * @author zmj
 */
@Aspect
public class ControllerAspector {

  @Pointcut("(@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)) && execution(public * *(..))")
  public void controllerPointCut() {

  }

  @Around(value = "controllerPointCut()")
  public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {

    Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

    Object[] args = joinPoint.getArgs();

    List<Object> argList = new LinkedList<>();

    if (logger.isDebugEnabled()) {
      String logMsg;
      if (!ArrayUtil.isEmpty(args)) {
        for (Object arg : args) {
          if (arg instanceof HttpServletRequest) {
            argList.add("request");
            continue;
          }

          if (arg instanceof HttpServletResponse) {
            argList.add("response");
            continue;
          }

          if (arg instanceof MultipartFile) {
            argList.add("file");
            continue;
          }

          argList.add(arg);
        }

        if (CollectionUtil.isEmpty(argList)) {
          logMsg = "null";
        } else {
          try {
            logMsg = JsonUtil.toJsonString(argList);
          } catch (Exception e) {
            logMsg = "unknown";
          }
        }
      } else {
        logMsg = "null";
      }

      logger.debug("Controller={},method={},请求参数={}", joinPoint.getTarget().getClass().getName(),
          joinPoint.getSignature().getName(), logMsg);
    } else {
      logger.info("Controller={},method={},开始请求", joinPoint.getTarget().getClass().getName(),
          joinPoint.getSignature().getName());
    }

    long begTime = System.currentTimeMillis();
    Object value = joinPoint.proceed();
    long invokeTime = System.currentTimeMillis() - begTime;

    if (logger.isDebugEnabled()) {
      logger.debug("Controller={},method={},返回参数={}", joinPoint.getTarget().getClass().getName(),
          joinPoint.getSignature().getName(), JsonUtil.toJsonString(value));
    } else {
      logger.info("Controller={},method={},响应成功", joinPoint.getTarget().getClass().getName(),
          joinPoint.getSignature().getName());
    }

    logger.info("Controller={},method={},响应时间={}ms", joinPoint.getTarget().getClass().getName(),
        joinPoint.getSignature().getName(), invokeTime);

    return value;
  }
}
