package com.lframework.starter.cloud.resp;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.core.components.resp.Response;
import com.lframework.starter.web.core.components.resp.ResponseErrorBuilder;

public class ApiInvokeResultErrorBuilderWrapper implements ResponseErrorBuilder {

  @Override
  public boolean isDefault() {
    return false;
  }

  @Override
  public boolean isMatch(Object controller) {
    return controller instanceof BaseFeignClient;
  }

  @Override
  public Response<Void> fail() {
    return ApiInvokeResultBuilder.fail();
  }

  @Override
  public Response<Void> fail(String msg) {
    return ApiInvokeResultBuilder.fail(msg);
  }

  @Override
  public <T> Response<T> fail(String msg, T data) {
    throw new DefaultSysException("失败不支持传递数据！");
  }

  @Override
  public Response<Void> fail(BaseException e) {
    return ApiInvokeResultBuilder.fail(e);
  }
}
