package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;

public class ExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof Exception;
  }

  @Override
  public BaseException convert(Throwable e) {
    return new DefaultSysException();
  }

  @Override
  public int getOrder() {
    return LAST_ORDER;
  }
}
