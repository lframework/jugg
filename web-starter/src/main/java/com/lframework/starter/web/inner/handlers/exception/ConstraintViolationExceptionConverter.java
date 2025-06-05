package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ConstraintViolationExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof ConstraintViolationException;
  }

  @Override
  public BaseException convert(Throwable e) {
    ConstraintViolationException ex = (ConstraintViolationException) e;
    for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
      return new InputErrorException(constraintViolation.getMessage());
    }

    return new InputErrorException();
  }

  @Override
  public int getOrder() {
    return THIRD_ORDER;
  }
}
