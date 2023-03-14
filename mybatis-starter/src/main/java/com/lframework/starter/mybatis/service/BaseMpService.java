package com.lframework.starter.mybatis.service;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lframework.starter.common.utils.BeanUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.constants.MyBatisStringPool;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import java.io.Serializable;
import java.util.Collection;

/**
 * Service基类
 *
 * @author zmj
 */
public interface BaseMpService<T> extends IService<T> {

  default <S> S getThis(Class<S> clazz) {

    return ApplicationUtil.getBean(clazz);
  }

  /**
   * 清除缓存
   * 仅适用于key是常量值的缓存
   */
  default void cleanCaches() {
    BaseMpService<T> thisService = getThis(getClass());
    thisService.cleanCacheByKey(null);
  }

  /**
   * 根据Key清除缓存
   *
   * @param key
   */
  default void cleanCacheByKey(Serializable key) {

  }

  /**
   * 批量根据Key清除缓存
   *
   * @param keys
   */
  default void cleanCacheByKeys(Collection<? extends Serializable> keys) {
    BaseMpService<T> thisService = getThis(getClass());
    if (CollectionUtil.isNotEmpty(keys)) {
      keys.forEach(thisService::cleanCacheByKey);
    }
  }

  /**
   * 复制新增记录内置字段
   *
   * @param source
   * @param target
   */
  default void copyCreateColumns(T source, T target) {
    CopyOptions options = CopyOptions.create()
        .setPropertiesFilter((k, v) -> MyBatisStringPool.COLUMN_CREATE_BY.equals(k.getName())
            || MyBatisStringPool.COLUMN_CREATE_BY_ID.equals(k.getName())
            || MyBatisStringPool.COLUMN_CREATE_TIME.equals(k.getName()));
    BeanUtil.copyProperties(source, target, options);
  }

  /**
   * 复制修改记录内置字段
   *
   * @param source
   * @param target
   */
  default void copyUpdateColumns(T source, T target) {
    CopyOptions options = CopyOptions.create()
        .setPropertiesFilter((k, v) -> MyBatisStringPool.COLUMN_UPDATE_BY.equals(k.getName())
            || MyBatisStringPool.COLUMN_UPDATE_BY_ID.equals(k.getName())
            || MyBatisStringPool.COLUMN_UPDATE_TIME.equals(k.getName()));
    BeanUtil.copyProperties(source, target, options);
  }

  /**
   * 复制新增和修改记录内置字段
   *
   * @param source
   * @param target
   */
  default void copyCreateAndUpdateColumns(T source, T target) {
    CopyOptions options = CopyOptions.create()
        .setPropertiesFilter((k, v) -> MyBatisStringPool.COLUMN_CREATE_BY.equals(k.getName())
            || MyBatisStringPool.COLUMN_CREATE_BY_ID.equals(k.getName())
            || MyBatisStringPool.COLUMN_CREATE_TIME.equals(k.getName())
            || MyBatisStringPool.COLUMN_UPDATE_BY.equals(k.getName())
            || MyBatisStringPool.COLUMN_UPDATE_BY_ID.equals(k.getName())
            || MyBatisStringPool.COLUMN_UPDATE_TIME.equals(k.getName()));
    BeanUtil.copyProperties(source, target, options);
  }
}
