package com.lframework.starter.redis.components.locker;

import com.lframework.common.locker.LockBuilder;
import com.lframework.common.locker.Locker;
import com.lframework.common.utils.IdUtil;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

@Component
@Conditional(RedisLockConditional.class)
public class RedisLockBuilder implements LockBuilder {

  @Resource(name = "redisTemplate")
  private RedisTemplate redisTemplate;

  private static final ThreadLocal<Map<String, Locker>> LOCKERS;

  static {
    LOCKERS = new ThreadLocal<>();
  }

  @Override
  public Locker buildLocker(String lockName, long expireTime, long waitTime) {

    Locker locker = LOCKERS.get().get(lockName);
    if (locker != null) {
      return locker;
    }

    locker = new RedisLocker(redisTemplate, lockName, expireTime, waitTime);
    LOCKERS.get().put(lockName, locker);

    return locker;
  }

  @Slf4j
  public static class RedisLocker implements Locker {


    private static final String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private static final Long INTERVAL_TIME = 100L;

    private RedisTemplate<String, Object> redisTemplate;

    private byte[] key;

    private byte[] requestId = IdUtil.getId().getBytes();

    private long expireTime;

    private boolean lock;

    private long waitTime;

    private int lockCount = 0;

    public RedisLocker(RedisTemplate<String, Object> redisTemplate, String key, long expireTime,
        long waitTime) {

      this.redisTemplate = redisTemplate;

      this.key = key.getBytes();

      this.expireTime = expireTime;

      this.waitTime = waitTime;
    }

    private class TryLockRedisCallBack implements RedisCallback<Boolean> {

      @Override
      public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

        if (log.isDebugEnabled()) {
          log.debug("key={}, requestId={}", key, requestId);
        }

        return connection.set(key, requestId, Expiration.from(expireTime, TimeUnit.MILLISECONDS),
            RedisStringCommands.SetOption.ifAbsent());
      }
    }

    private class ReleaseRedisCallBack implements RedisCallback<Boolean> {

      @Override
      public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

        return connection.eval(SCRIPT.getBytes(), ReturnType.BOOLEAN, 1, key, requestId);
      }
    }

    @Override
    public boolean lock() {

      if (this.lock) {
        addLockCount();
        return true;
      }

      while (!(this.lock = this.redisTemplate.execute(new TryLockRedisCallBack()))
          && this.waitTime > 0L) {
        try {
          Thread.sleep(INTERVAL_TIME);
          this.waitTime -= INTERVAL_TIME;
        } catch (InterruptedException e) {
          log.error("获取lock中断", e);
          return false;
        }
      }

      if (log.isDebugEnabled()) {
        log.debug("获取lock完成，result={}, key={}, requestId={}", this.lock, this.key, this.requestId);
      }

      if (this.lock) {
        addLockCount();
      }
      return this.lock;
    }

    private void addLockCount() {

      this.lockCount++;
      if (log.isDebugEnabled()) {
        log.debug("重入次数：{}", this.lockCount);
      }
    }

    private void reduceLockCount() {

      this.lockCount--;
      if (log.isDebugEnabled()) {
        log.debug("重入次数：{}", this.lockCount);
      }
    }

    @Override
    public boolean unLock() {

      if (this.lock) {
        if (this.lockCount <= 1) {
          boolean release = this.redisTemplate.execute(new ReleaseRedisCallBack());
          if (log.isDebugEnabled()) {
            log.debug("释放lock完成，result={}, key={}, requestId={}", this.lock, this.key,
                this.requestId);
          }

          this.lock = !release;

          reduceLockCount();

          RedisLockBuilder.LOCKERS.get().remove(this.key);

          return release;
        } else {

          reduceLockCount();
          return true;
        }
      }

      return true;
    }

    @Override
    public void close() {

      unLock();
    }
  }
}
