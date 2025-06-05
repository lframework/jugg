package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.AccessDeniedException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;

/**
 * 处理无权限异常
 */
public class AccessDeniedExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof AccessDeniedException;
  }

  @Override
  public BaseException convert(Throwable e) {
    return new AccessDeniedException();
  }
}
