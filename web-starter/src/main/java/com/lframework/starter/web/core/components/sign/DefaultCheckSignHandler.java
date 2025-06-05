package com.lframework.starter.web.core.components.sign;

import com.lframework.starter.web.inner.vo.openapi.OpenApiReqVo;

public class DefaultCheckSignHandler implements CheckSignHandler {

  @Override
  public boolean check(OpenApiReqVo req) {

    return false;
  }

  @Override
  public void setTenantId(OpenApiReqVo req) {

  }
}
