package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;
import javax.validation.UnexpectedTypeException;

/**
 * 处理传入参数类型转换错误异常
 */
public class UnexpectedTypeExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof UnexpectedTypeException;
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
