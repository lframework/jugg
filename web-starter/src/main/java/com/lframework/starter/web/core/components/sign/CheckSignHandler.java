package com.lframework.starter.web.core.components.sign;

import com.lframework.starter.web.inner.vo.openapi.OpenApiReqVo;

public interface CheckSignHandler {

  boolean check(OpenApiReqVo req);

  void setTenantId(OpenApiReqVo req);
}
