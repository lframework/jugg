package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class MethodArgumentNotValidExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof MethodArgumentNotValidException;
  }

  @Override
  public BaseException convert(Throwable e) {
    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      return new InputErrorException(error.getDefaultMessage());
    }

    return new InputErrorException();
  }

  @Override
  public int getOrder() {
    return THIRD_ORDER;
  }
}
