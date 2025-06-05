package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * 处理由于传入参数类型不匹配导致的异常
 */
public class HttpMessageNotReadableExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof HttpMessageNotReadableException;
  }

  @Override
  public BaseException convert(Throwable e) {
    return new InputErrorException();
  }

  @Override
  public int getOrder() {
    return THIRD_ORDER;
  }
}
