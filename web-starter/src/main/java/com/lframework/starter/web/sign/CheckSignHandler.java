package com.lframework.starter.web.sign;

import com.lframework.starter.web.vo.OpenApiReqVo;

public interface CheckSignHandler {

  boolean check(OpenApiReqVo req);

  void setTenantId(OpenApiReqVo req);
}
