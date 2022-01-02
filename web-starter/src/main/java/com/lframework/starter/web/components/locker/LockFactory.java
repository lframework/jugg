package com.lframework.starter.web.components.locker;

import com.lframework.common.locker.LockBuilder;
import com.lframework.common.locker.Locker;
import com.lframework.common.utils.Assert;
import com.lframework.starter.web.utils.ApplicationUtil;
import org.springframework.stereotype.Component;

@Component
public class LockFactory {

    /**
     * 默认获取锁等待时间 毫秒
     */
    public static final long DEFAULT_WAIT_TIME = 5000L;

    /**
     * 默认锁过期时间 毫秒
     */
    public static final long DEFAULT_EXPIRE_TIME = 30000L;

    public static Locker getLocker(String lockName) {

        return getLocker(lockName, DEFAULT_EXPIRE_TIME, DEFAULT_WAIT_TIME);
    }

    public static Locker getLocker(String lockName, long expireTime) {

        return getLocker(lockName, expireTime, DEFAULT_WAIT_TIME);
    }

    public static Locker getLocker(String lockName, long expireTime, long waitTime) {

        Assert.greaterThanZero(expireTime);
        Assert.greaterThanZero(waitTime);

        return ApplicationUtil.getBean(LockBuilder.class).buildLocker(lockName, expireTime, waitTime);
    }
}
