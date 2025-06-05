package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * 处理由于传入方式错误导致的异常
 */
public class HttpRequestMethodNotSupportedExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof HttpRequestMethodNotSupportedException;
  }

  @Override
  public BaseException convert(Throwable e) {
    return new DefaultSysException();
  }

  @Override
  public int getOrder() {
    return THIRD_ORDER;
  }
}
