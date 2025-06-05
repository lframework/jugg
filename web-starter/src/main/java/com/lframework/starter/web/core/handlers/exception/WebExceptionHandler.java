package com.lframework.starter.web.core.handlers.exception;

import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.core.components.resp.Response;
import com.lframework.starter.web.core.components.resp.ResponseErrorBuilder;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.ResponseUtil;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

/**
 * 异常处理器
 *
 * @author zmj
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {

  /**
   * 处理Throwable
   *
   * @param e
   * @param method
   * @return
   */
  @ExceptionHandler(Throwable.class)
  public Response throwableHandle(Throwable e, HandlerMethod method) {

    this.logException(e, method);

    Collection<WebExceptionConverter> converters = ApplicationUtil.getBeansOfType(
        WebExceptionConverter.class).values();

    BaseException ex = null;
    converters = converters.stream().sorted(Comparator.comparingInt(WebExceptionConverter::getOrder)).collect(
        Collectors.toList());

    for (WebExceptionConverter converter : converters) {
      if (converter.isMatch(e)) {
        log.debug("WebExceptionConverter = {}", converter.getClass().getName());
        ex = converter.convert(e);
        break;
      }
    }
    if (ex == null) {
      ex = new DefaultSysException("无法匹配WebExceptionConverter");
    }
    this.setResponseCode(ex);

    return getBuilder(method.getBean()).fail(ex);
  }

  protected void logException(Throwable e, HandlerMethod method) {

    if (e instanceof ClientException) {
      if (log.isDebugEnabled()) {
        String className = method.getBeanType().getName();
        String methodName = method.getMethod().getName();
        log.debug("className={}, methodName={}, 有异常产生", className, methodName, e);
      }
    } else {
      String className = method.getBeanType().getName();
      String methodName = method.getMethod().getName();
      log.error("className={}, methodName={}, 有异常产生", className, methodName, e);
    }
  }

  protected void setResponseCode(BaseException e) {

    ResponseUtil.getResponse().setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  protected ResponseErrorBuilder getBuilder(Object bean) {

    Map<String, ResponseErrorBuilder> builders = ApplicationUtil.getBeansOfType(
        ResponseErrorBuilder.class);
    ResponseErrorBuilder builder = null;
    for (ResponseErrorBuilder value : builders.values()) {
      if (value.isDefault()) {
        builder = value;
        break;
      }
    }

    for (ResponseErrorBuilder value : builders.values()) {
      if (value.isMatch(bean)) {
        builder = value;
        break;
      }
    }

    return builder;
  }
}
