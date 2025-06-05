package com.lframework.starter.web.core.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.ReflectUtil;
import com.lframework.starter.web.core.constants.SqlMethodConstants;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.service.BaseMpService;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Objects;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseMpServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T>
    implements BaseMpService<T> {

  @Transactional(rollbackFor = Exception.class)
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

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveOrUpdateAllColumnBatch(Collection<T> entityList, int batchSize) {
    TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
    Assert.notNull(tableInfo,
        "error: can not execute. because can not find cache of TableInfo for entity!");
    String keyProperty = tableInfo.getKeyProperty();
    Assert.notEmpty(keyProperty,
        "error: can not execute. because can not find column for id from entity!");
    return SqlHelper.saveOrUpdateBatch(this.entityClass, this.mapperClass, this.log, entityList,
        batchSize, (sqlSession, entity) -> {
          Object idVal = ReflectionKit.getFieldValue(entity, keyProperty);
          return StringUtils.checkValNull(idVal)
              || CollectionUtils.isEmpty(
              sqlSession.selectList(getSqlStatement(SqlMethod.SELECT_BY_ID), entity));
        }, (sqlSession, entity) -> {
          MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
          param.put(Constants.ENTITY, entity);
          sqlSession.update(getSqlStatement(SqlMethodConstants.UPDATE_ALL_COLUMN_BY_ID), param);
        });
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateAllColumnBatchById(Collection<T> entityList, int batchSize) {
    String sqlStatement = getSqlStatement(SqlMethodConstants.UPDATE_ALL_COLUMN_BY_ID);
    return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
      MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
      param.put(Constants.ENTITY, entity);
      sqlSession.update(sqlStatement, param);
    });
  }

  @Override
  public BaseMapper<T> getBaseMpMapper() {
    return baseMapper;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveOrUpdateAllColumn(T entity) {
    if (null != entity) {
      TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
      Assert.notNull(tableInfo,
          "error: can not execute. because can not find cache of TableInfo for entity!");
      String keyProperty = tableInfo.getKeyProperty();
      Assert.notEmpty(keyProperty,
          "error: can not execute. because can not find column for id from entity!");
      Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
      return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))
          ? save(entity) : updateAllColumnById(entity);
    }
    return false;
  }

  /**
   * 获取mapperStatementId
   *
   * @param sqlMethod 方法名
   * @return 命名id
   * @since 3.4.0
   */
  protected String getSqlStatement(String sqlMethod) {
    return mapperClass.getName() + StringPool.DOT + sqlMethod;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateAllColumn(T entity, Wrapper<T> updateWrapper) {
    return SqlHelper.retBool(getBaseMapper().updateAllColumn(entity, updateWrapper));
  }
}
