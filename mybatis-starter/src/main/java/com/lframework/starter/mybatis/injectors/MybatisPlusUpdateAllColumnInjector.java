package com.lframework.starter.mybatis.injectors;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.lframework.starter.mybatis.constants.SqlMethodConstants;
import java.util.List;
import java.util.function.Predicate;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.springframework.stereotype.Component;

@Component
public class MybatisPlusUpdateAllColumnInjector extends DefaultSqlInjector {

  @Override
  public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
    List<AbstractMethod> methods = super.getMethodList(mapperClass);
    methods.add(new UpdateAllColumnById());
    return methods;
  }

  /**
   * 自定义更新所有字段的方法
   */
  static class UpdateAllColumnById extends AbstractMethod {

    /**
     * 字段筛选条件
     */
    @Setter
    @Accessors(chain = true)
    private Predicate<TableFieldInfo> predicate;

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass,
        TableInfo tableInfo) {
      SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
      final String additional = optlockVersion(tableInfo) + tableInfo.getLogicDeleteSql(true, true);
      String sqlSet = this.filterTableFieldInfo(tableInfo.getFieldList(), getPredicate(),
          i -> i.getSqlSet(true, ENTITY_DOT), NEWLINE);
      sqlSet = SqlScriptUtils.convertSet(sqlSet);
      String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet,
          tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty(), additional);
      SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
      return addUpdateMappedStatement(mapperClass, modelClass,
          SqlMethodConstants.UPDATE_ALL_COLUMN_BY_ID, sqlSource);
    }

    private Predicate<TableFieldInfo> getPredicate() {
      Predicate<TableFieldInfo> noLogic = t -> !t.isLogicDelete();
      if (predicate != null) {
        return noLogic.and(predicate);
      }
      return noLogic;
    }
  }
}

