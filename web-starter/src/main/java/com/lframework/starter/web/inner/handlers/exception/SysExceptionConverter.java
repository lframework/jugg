package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.SysException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;

/**
 * 处理系统异常
 */
public class SysExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof SysException;
  }

  @Override
  public BaseException convert(Throwable e) {
    return new DefaultSysException();
  }

  @Override
  public int getOrder() {
    return SECOND_ORDER;
  }
}
