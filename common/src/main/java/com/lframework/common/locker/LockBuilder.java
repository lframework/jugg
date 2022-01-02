package com.lframework.common.locker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface LockBuilder {

    Locker buildLocker(String lockName, long expireTime, long waitTime);
}
