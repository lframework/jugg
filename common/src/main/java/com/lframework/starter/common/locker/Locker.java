package com.lframework.starter.common.locker;

public interface Locker extends AutoCloseable {

  boolean lock();

  boolean unLock();
}
