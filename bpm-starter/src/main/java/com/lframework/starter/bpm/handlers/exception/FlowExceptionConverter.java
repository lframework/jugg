package com.lframework.starter.bpm.handlers.exception;

import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.handlers.exception.WebExceptionConverter;
import org.dromara.warm.flow.core.exception.FlowException;

public class FlowExceptionConverter implements WebExceptionConverter {

  @Override
  public boolean isMatch(Throwable e) {
    return e instanceof FlowException;
  }

  @Override
  public BaseException convert(Throwable e) {
    return new DefaultClientException(e.getMessage());
  }
}
