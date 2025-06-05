package com.lframework.starter.web.inner.components.oplog;


import com.lframework.starter.web.core.components.oplog.OpLogType;

public class OtherOpLogType implements OpLogType {

  @Override
  public Integer getCode() {
    return 99;
  }
}
