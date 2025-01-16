package com.lframework.starter.common.locker;

public interface LockBuilder {

  /**
   * 构建锁
   *
   * @param lockName   锁名称
   * @param expireTime 过期时间（毫秒）只有Redis锁会生效
   * @param waitTime   等待时间（毫秒）只有Redis锁会生效
   * @return
   */
  Locker buildLocker(String lockName, long expireTime, long waitTime);
}
