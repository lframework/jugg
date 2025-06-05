package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;

/**
 * 处理系统异常
 */
public class ClientExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof ClientException;
  }

  @Override
  public BaseException convert(Throwable e) {
    return (ClientException) e;
  }

  @Override
  public int getOrder() {
    return SECOND_ORDER;
  }
}
