package com.lframework.starter.web.sign;

import com.lframework.starter.web.vo.OpenApiReqVo;
import javax.servlet.http.HttpServletRequest;

public class DefaultCheckSignHandler implements CheckSignHandler {

  @Override
  public boolean check(OpenApiReqVo req) {

    return false;
  }

  @Override
  public void setTenantId(OpenApiReqVo req) {

  }
}
