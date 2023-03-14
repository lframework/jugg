package com.lframework.starter.common.utils;

import cn.hutool.core.lang.Snowflake;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdWorker extends Snowflake {

  public IdWorker() {
    super();
  }

  public IdWorker(long workerId) {
    super(workerId);
  }

  public IdWorker(long workerId, long dataCenterId) {
    super(workerId, dataCenterId);
  }

  public IdWorker(long workerId, long dataCenterId, boolean isUseSystemClock) {
    super(workerId, dataCenterId, isUseSystemClock);
  }

  public IdWorker(Date epochDate, long workerId, long dataCenterId,
      boolean isUseSystemClock) {
    super(epochDate, workerId, dataCenterId, isUseSystemClock);
  }

  public IdWorker(Date epochDate, long workerId, long dataCenterId,
      boolean isUseSystemClock, long timeOffset) {
    super(epochDate, workerId, dataCenterId, isUseSystemClock, timeOffset);
  }
}
