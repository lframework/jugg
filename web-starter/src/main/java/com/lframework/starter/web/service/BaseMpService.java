package com.lframework.starter.web.service;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lframework.starter.common.utils.BeanUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.constants.MyBatisStringPool;
import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

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
   * 清除缓存 仅适用于key是常量值的缓存
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

  // 这里只有复制新增记录的内置字段，因为修改记录的内置字段无论何时都会被覆盖

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
   * 批量修改插入
   *
   * @param entityList 实体对象集合
   */
  @Transactional(rollbackFor = Exception.class)
  default boolean saveOrUpdateAllColumnBatch(Collection<T> entityList) {
    return saveOrUpdateAllColumnBatch(entityList, IService.DEFAULT_BATCH_SIZE);
  }

  /**
   * 批量修改插入
   *
   * @param entityList 实体对象集合
   * @param batchSize  每次的数量
   */
  boolean saveOrUpdateAllColumnBatch(Collection<T> entityList, int batchSize);

  /**
   * 根据ID 批量更新
   *
   * @param entityList 实体对象集合
   */
  @Transactional(rollbackFor = Exception.class)
  default boolean updateAllColumnBatchById(Collection<T> entityList) {
    return updateAllColumnBatchById(entityList, IService.DEFAULT_BATCH_SIZE);
  }

  /**
   * 根据ID 批量更新
   *
   * @param entityList 实体对象集合
   * @param batchSize  更新批次数量
   */
  boolean updateAllColumnBatchById(Collection<T> entityList, int batchSize);

  /**
   * TableId 注解存在更新记录，否插入一条记录
   *
   * @param entity 实体对象
   */
  boolean saveOrUpdateAllColumn(T entity);

  /**
   * 根据 ID 选择修改
   *
   * @param entity 实体对象
   */
  default boolean updateAllColumnById(T entity) {
    return SqlHelper.retBool(getBaseMpMapper().updateAllColumnById(entity));
  }

  /**
   * 获取对应 entity 的 BaseMapper
   *
   * @return BaseMapper
   */
  BaseMapper<T> getBaseMpMapper();

  /**
   * 根据 whereEntity 条件，更新记录
   *
   * @param entity        实体对象
   * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
   */
  boolean updateAllColumn(T entity, Wrapper<T> updateWrapper);
}
