package com.lframework.starter.web.core.components.locker;

import com.lframework.starter.common.locker.LockBuilder;
import com.lframework.starter.common.locker.Locker;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultLockBuilder implements LockBuilder {

  private static final Map<String, Locker> LOCKERS;

  static {
    LOCKERS = new ConcurrentHashMap<>();
  }

  @Override
  public Locker buildLocker(String lockName, long expireTime, long waitTime) {

    Locker locker = LOCKERS.get(lockName);
    if (locker != null) {
      return locker;
    }

    synchronized (DefaultLockBuilder.class) {
      locker = LOCKERS.get(lockName);
      if (locker != null) {
        return locker;
      }

      locker = new JavaLocker(lockName, waitTime);

      LOCKERS.put(lockName, locker);
    }

    return locker;
  }

  public static class JavaLocker implements Locker {

    private String lockName;

    private long waitTime;

    private ReentrantLock lock;

    public JavaLocker(String lockName, long waitTime) {

      this.waitTime = waitTime;
      this.lockName = lockName;
      this.lock = new ReentrantLock();
    }

    @Override
    public boolean lock() {

      try {
        return this.lock.tryLock(waitTime, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        log.error(e.getMessage(), e);
        return false;
      }
    }

    @Override
    public boolean unLock() {

      this.lock.unlock();

      DefaultLockBuilder.LOCKERS.remove(this.lockName);

      return true;
    }

    @Override
    public void close() {

      unLock();
    }
  }
}
