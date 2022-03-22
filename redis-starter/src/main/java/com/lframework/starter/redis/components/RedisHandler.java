package com.lframework.starter.redis.components;

import com.lframework.common.utils.Assert;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Redis处理器
 *
 * @author zmj
 */
@Component
public class RedisHandler {

  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 指定缓存失效时间
   *
   * @param key
   * @param time 时间（毫秒）
   * @return
   */
  public boolean expire(String key, long time) {

    Assert.notNull(key);
    if (time < 0) {
      //若time < 0 则立即失效
      time = 1;
    }

    return redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
  }

  /**
   * 根据key 获取过期时间
   *
   * @param key
   * @return 时间（毫秒） 返回0代表永久有效
   */
  public long getExpire(String key) {

    Assert.notNull(key);
    return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
  }

  /**
   * 查看key是否存在
   *
   * @param key
   * @return
   */
  public boolean hasKey(String key) {

    Assert.notNull(key);
    return redisTemplate.hasKey(key);
  }

  /**
   * 删除缓存
   *
   * @param keys
   */
  public void del(String... keys) {

    Assert.notNull(keys);

    redisTemplate.delete(CollectionUtils.arrayToList(keys));
  }

  //String

  /**
   * 普通缓存获取
   *
   * @param key
   * @return
   */
  public Object get(String key) {

    Assert.notNull(key);

    return redisTemplate.opsForValue().get(key);
  }

  /**
   * 增加普通缓存
   *
   * @param key
   * @param value
   */
  public void set(String key, Object value) {

    Assert.notNull(key);
    redisTemplate.opsForValue().set(key, value);
  }

  /**
   * 增加普通缓存并设置过期时间
   *
   * @param key
   * @param value
   * @param time  小于0时则为永久有效
   */
  public void set(String key, Object value, long time) {

    Assert.notNull(key);
    if (time < 0) {
      set(key, value);
    } else {
      redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }
  }

  /**
   * 递增
   *
   * @param key
   * @param delta 增加量
   * @return
   */
  public long incr(String key, long delta) {

    Assert.notNull(key);
    Assert.greaterThanZero(delta);

    return redisTemplate.opsForValue().increment(key, delta);
  }

  /**
   * 递减
   *
   * @param key
   * @param delta 减少量
   * @return
   */
  public long decr(String key, long delta) {

    Assert.notNull(key);
    Assert.greaterThanZero(delta);

    return redisTemplate.opsForValue().increment(key, -delta);
  }

  /**
   * 递增
   *
   * @param key
   * @param delta 增加量
   * @return
   */
  public double incr(String key, double delta) {

    Assert.notNull(key);
    Assert.greaterThanZero(delta);

    return redisTemplate.opsForValue().increment(key, delta);
  }

  /**
   * 递减
   *
   * @param key
   * @param delta 减少量
   * @return
   */
  public double decr(String key, double delta) {

    Assert.notNull(key);
    Assert.greaterThanZero(delta);

    return redisTemplate.opsForValue().increment(key, -delta);
  }

  //Map

  /**
   * HashGet
   *
   * @param key  map所对应的key值
   * @param item 获取的数据在map中所对应的key值
   * @return
   */
  public Object hget(String key, String item) {

    Assert.notNull(key);
    Assert.notNull(item);
    return redisTemplate.opsForHash().get(key, item);
  }

  /**
   * 获取hashKey对应的所有键值
   *
   * @param key
   * @return
   */
  public Map<Object, Object> hmget(String key) {

    Assert.notNull(key);
    return redisTemplate.opsForHash().entries(key);
  }

  /**
   * 缓存map
   *
   * @param key
   * @param map
   */
  public void hmset(String key, Map<String, Object> map) {

    Assert.notNull(key);
    Assert.notNull(map);

    redisTemplate.opsForHash().putAll(key, map);
  }

  /**
   * 向一张hash表中放入数据,如果不存在将创建
   *
   * @param key
   * @param item
   * @param value
   */
  public void hset(String key, String item, Object value) {

    Assert.notNull(key);
    Assert.notNull(item);
    redisTemplate.opsForHash().put(key, item, value);
  }

  /**
   * 向一张hash表中放入数据,如果不存在将创建
   *
   * @param key
   * @param item
   * @param value
   * @param time  过期时间（毫秒） 如果已存在的hash表有过期时间，将会替换原有时间
   * @return
   */
  public boolean hset(String key, String item, Object value, long time) {

    Assert.notNull(key);
    Assert.notNull(item);
    redisTemplate.opsForHash().put(key, item, value);
    if (time < 0) {
      time = 1;
    }

    return expire(key, time);
  }

  /**
   * 删除hash表中的值
   *
   * @param key
   * @param items
   */
  public void hdel(String key, String... items) {

    Assert.notNull(key);
    Assert.notEmpty(items);

    redisTemplate.opsForHash().delete(key, items);
  }

  /**
   * 判断hash表中是否有该项的值
   *
   * @param key
   * @param item
   * @return
   */
  public boolean hHasKey(String key, String item) {

    Assert.notNull(key);
    Assert.notNull(item);

    return redisTemplate.opsForHash().hasKey(key, item);
  }

  /**
   * hash递增 如果不存在,就会创建一个 并把新增后的值返回
   *
   * @param key
   * @param item
   * @param by
   * @return
   */
  public double hincr(String key, String item, double by) {

    Assert.notNull(key);
    Assert.notNull(item);
    return redisTemplate.opsForHash().increment(key, item, by);
  }

  /**
   * hash递减
   *
   * @param key
   * @param item
   * @param by
   * @return
   */
  public double hdecr(String key, String item, double by) {

    Assert.notNull(key);
    Assert.notNull(item);
    return redisTemplate.opsForHash().increment(key, item, -by);
  }

  //set

  /**
   * 根据key获取Set中的所有值
   *
   * @param key
   * @return
   */
  public Set<Object> sGet(String key) {

    Assert.notNull(key);
    return redisTemplate.opsForSet().members(key);
  }

  /**
   * 根据value从一个set中查询,是否存在
   *
   * @param key
   * @param value
   * @return
   */
  public boolean sHasKey(String key, Object value) {

    Assert.notNull(key);
    return redisTemplate.opsForSet().isMember(key, value);
  }

  /**
   * 将数据放入set缓存
   *
   * @param key
   * @param values
   * @return 成功个数
   */
  public long sSet(String key, Object... values) {

    Assert.notNull(key);
    return redisTemplate.opsForSet().add(key, values);
  }

  /**
   * 将set数据放入缓存
   *
   * @param key
   * @param time   时间（毫秒）
   * @param values
   * @return 成功个数
   */
  public long sSetAndExpire(String key, long time, Object... values) {

    Assert.notNull(key);
    long count = redisTemplate.opsForSet().add(key, values);
    if (time < 0) {
      time = 1;
    }

    expire(key, time);
    return count;
  }

  /**
   * 获取set缓存的长度
   *
   * @param key
   * @return
   */
  public long sGetSetSize(String key) {

    Assert.notNull(key);
    return redisTemplate.opsForSet().size(key);
  }

  /**
   * 移除值
   *
   * @param key
   * @param values
   * @return 移除的个数
   */
  public long setRemove(String key, Object... values) {

    Assert.notNull(key);
    long count = redisTemplate.opsForSet().remove(key, values);
    return count;
  }

  //list

  /**
   * 获取list缓存的内容
   *
   * @param key
   * @param start
   * @param end
   * @return
   */
  public List<Object> lGet(String key, long start, long end) {

    Assert.notNull(key);
    Assert.greaterThanOrEqualToZero(start);
    Assert.greaterThanOrEqualToZero(end);
    return redisTemplate.opsForList().range(key, start, end);
  }

  /**
   * 获取list缓存的长度
   *
   * @param key
   * @return
   */
  public long lGetListSize(String key) {

    return redisTemplate.opsForList().size(key);
  }

  /**
   * 通过索引 获取list中的值
   *
   * @param key
   * @param index
   * @return
   */
  public Object lGetIndex(String key, long index) {

    Assert.notNull(key);
    Assert.greaterThanOrEqualToZero(index);
    return redisTemplate.opsForList().index(key, index);
  }

  /**
   * 增加缓存至list中
   *
   * @param key
   * @param value
   */
  public void lSet(String key, Object value) {

    Assert.notNull(key);
    redisTemplate.opsForList().rightPush(key, value);
  }

  /**
   * 增加缓存至list中，并对list设置过期时间
   *
   * @param key
   * @param value
   * @param time  过期时间（毫秒）
   * @return
   */
  public boolean lSet(String key, Object value, long time) {

    Assert.notNull(key);
    redisTemplate.opsForList().rightPush(key, value);
    if (time < 0) {
      time = 1;
    }

    return expire(key, time);
  }

  /**
   * 将list放入缓存
   *
   * @param key
   * @param value
   */
  public void lSet(String key, List<Object> value) {

    Assert.notNull(key);
    redisTemplate.opsForList().rightPushAll(key, value);
  }

  /**
   * 将list放入缓存
   *
   * @param key
   * @param value
   * @param time  失效时间（毫秒）
   * @return
   */
  public boolean lSet(String key, List<Object> value, long time) {

    Assert.notNull(key);
    redisTemplate.opsForList().rightPushAll(key, value);

    if (time < 0) {
      time = 1;
    }

    return expire(key, time);
  }

  /**
   * 根据索引修改list中的某条数据
   *
   * @param key
   * @param index
   * @param value
   */
  public void lUpdateIndex(String key, long index, Object value) {

    Assert.notNull(key);
    Assert.greaterThanOrEqualToZero(index);
    redisTemplate.opsForList().set(key, index, value);
  }

  /**
   * 移除N个值为value
   *
   * @param key
   * @param count
   * @param value
   * @return
   */
  public long lRemove(String key, long count, Object value) {

    Assert.notNull(key);
    Assert.greaterThanZero(count);
    long remove = redisTemplate.opsForList().remove(key, count, value);
    return remove;
  }
}
