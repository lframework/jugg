package com.lframework.common.locker;

public interface Locker extends AutoCloseable {

    boolean lock();

    boolean unLock();
}
