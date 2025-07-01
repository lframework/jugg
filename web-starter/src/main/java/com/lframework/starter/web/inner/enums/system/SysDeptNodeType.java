package com.lframework.starter.web.inner.enums.system;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public final class SysDeptNodeType implements NodeType, Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public Integer getCode() {

    return 1;
  }

  @Override
  public String getDesc() {

    return "部门";
  }
}
