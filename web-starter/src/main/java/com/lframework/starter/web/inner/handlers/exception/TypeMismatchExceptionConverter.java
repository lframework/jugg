package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 处理由于传入参数类型不匹配导致的异常
 */
public class TypeMismatchExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof TypeMismatchException;
  }

  @Override
  public BaseException convert(Throwable e) {
    MethodParameter methodParameter = null;
    if (e instanceof MethodArgumentConversionNotSupportedException) {
      methodParameter = ((MethodArgumentConversionNotSupportedException) e).getParameter();
    } else if (e instanceof MethodArgumentTypeMismatchException) {
      methodParameter = ((MethodArgumentTypeMismatchException) e).getParameter();
    }

    if (methodParameter != null) {
      TypeMismatch typeMismatch = methodParameter.getMethod()
          .getParameters()[methodParameter.getParameterIndex()].getAnnotation(TypeMismatch.class);
      if (typeMismatch != null) {
        return new InputErrorException(typeMismatch.message());
      }
    }

    return new InputErrorException();
  }

  @Override
  public int getOrder() {
    return THIRD_ORDER;
  }
}
