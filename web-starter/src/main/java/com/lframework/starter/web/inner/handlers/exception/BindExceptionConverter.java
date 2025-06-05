package com.lframework.starter.web.inner.handlers.exception;


import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * 处理由于传入方式错误导致的异常
 */
public class BindExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof BindException;
  }

  @Override
  public BaseException convert(Throwable e) {

    BindException ex = (BindException) e;
    if (ex.getErrorCount() > 0) {
      ObjectError objectError = ex.getAllErrors().get(0);

      if (objectError instanceof FieldError && "typeMismatch".equals(objectError.getCode())) {
        String fieldName = ((FieldError) objectError).getField();

        Class targetClazz = ex.getBindingResult().getTarget().getClass();

        TypeMismatch typeMismatch = null;
        try {
          typeMismatch = targetClazz.getDeclaredField(fieldName).getAnnotation(TypeMismatch.class);
        } catch (NoSuchFieldException exp) {
          throw new DefaultSysException(exp.getMessage());
        }
        if (typeMismatch != null) {
          return new InputErrorException(typeMismatch.message());
        }
      }
    }
    for (ObjectError error : ex.getAllErrors()) {
      return new InputErrorException(error.getDefaultMessage());
    }

    return new InputErrorException();
  }

  @Override
  public int getOrder() {
    return THIRD_ORDER;
  }
}
