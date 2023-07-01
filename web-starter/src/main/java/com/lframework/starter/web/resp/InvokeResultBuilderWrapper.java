package com.lframework.starter.web.resp;

import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.web.controller.BaseController;

public class InvokeResultBuilderWrapper implements ResponseBuilder {

  @Override
  public boolean isDefault() {
    return true;
  }

  @Override
  public boolean isMatch(Object controller) {
    return controller instanceof BaseController;
  }

  @Override
  public Response<Void> success() {
    return InvokeResultBuilder.success();
  }

  @Override
  public <T> Response<T> success(T data) {
    return InvokeResultBuilder.success(data);
  }

  @Override
  public Response<Void> fail() {
    return InvokeResultBuilder.fail();
  }

  @Override
  public Response<Void> fail(String msg) {
    return InvokeResultBuilder.fail(msg);
  }

  @Override
  public <T> Response<T> fail(String msg, T data) {
    return InvokeResultBuilder.fail(msg, data);
  }

  @Override
  public Response<Void> fail(BaseException e) {
    return InvokeResultBuilder.fail(e);
  }
}
