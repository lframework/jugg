package com.lframework.starter.common.locker;

public interface LockBuilder {

  Locker buildLocker(String lockName, long expireTime, long waitTime);
}
