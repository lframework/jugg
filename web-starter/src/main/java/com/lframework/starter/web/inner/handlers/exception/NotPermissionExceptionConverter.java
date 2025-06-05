package com.lframework.starter.web.inner.handlers.exception;


import cn.dev33.satoken.exception.NotPermissionException;
import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.AccessDeniedException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;

/**
 * 处理无权限异常
 */
public class NotPermissionExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof NotPermissionException;
  }

  @Override
  public BaseException convert(Throwable e) {
    return new AccessDeniedException();
  }

  @Override
  public int getOrder() {
    return THIRD_ORDER;
  }
}
