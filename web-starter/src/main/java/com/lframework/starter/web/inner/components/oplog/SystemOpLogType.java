package com.lframework.starter.web.inner.components.oplog;


import com.lframework.starter.web.core.components.oplog.OpLogType;

public class SystemOpLogType implements OpLogType {

  @Override
  public Integer getCode() {
    return 2;
  }
}
