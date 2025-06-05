package com.lframework.starter.web.core.mapper;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.ReflectUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseMapper 所有Mapper的基类
 *
 * @param <T>
 * @author zmj
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

  Logger LOGGER = LoggerFactory.getLogger(BaseMapper.class);

  /**
   * 根据 whereEntity 条件，更新记录
   *
   * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
   * @return
   */
  default int update(Wrapper<T> updateWrapper) {
    T entity = null;
    try {
      if (updateWrapper instanceof AbstractWrapper) {
        entity = (T) ((AbstractWrapper) updateWrapper).getEntityClass().newInstance();
        // 将所有的值设置为null
        Field[] fields = ReflectUtil.getFields(entity.getClass());
        if (ArrayUtil.isNotEmpty(fields)) {
          for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
              continue;
            }
            ReflectUtil.setFieldValue(entity, field, null);
          }
        }
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }

    return update(entity, updateWrapper);
  }

  /**
   * 根据ID更新所有字段 此方法不会忽略null值字段
   *
   * @param entity
   * @return
   */
  int updateAllColumnById(@Param(Constants.ENTITY) T entity);

  /**
   * 根据 whereEntity 条件，更新记录 此方法不会忽略null值字段
   *
   * @param entity
   * @return
   */
  int updateAllColumn(@Param(Constants.ENTITY) T entity,
      @Param(Constants.WRAPPER) Wrapper<T> updateWrapper);
}
