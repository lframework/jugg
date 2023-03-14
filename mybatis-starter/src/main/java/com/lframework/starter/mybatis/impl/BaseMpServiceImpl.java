package com.lframework.starter.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.ReflectUtil;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class BaseMpServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T>
    implements BaseMpService<T> {

  @Override
  public boolean update(Wrapper<T> updateWrapper) {
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
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }

    return super.update(entity, updateWrapper);
  }
}
