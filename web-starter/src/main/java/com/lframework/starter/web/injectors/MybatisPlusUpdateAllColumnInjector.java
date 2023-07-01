package com.lframework.starter.web.injectors;

import static java.util.stream.Collectors.joining;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.lframework.starter.web.constants.SqlMethodConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class MybatisPlusUpdateAllColumnInjector extends DefaultSqlInjector {

  @Override
  public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
    List<AbstractMethod> methods = super.getMethodList(mapperClass);
    methods.add(new UpdateAllColumnById());
    methods.add(new UpdateAllColumn());
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
          i -> i.getSqlSet(true, Constants.ENTITY_DOT), StringPool.NEWLINE);
      sqlSet = SqlScriptUtils.convertSet(sqlSet);
      String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet,
          tableInfo.getKeyColumn(), Constants.ENTITY_DOT + tableInfo.getKeyProperty(), additional);
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

  /**
   * 自定义更新所有字段的方法
   */
  static class UpdateAllColumn extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass,
        TableInfo tableInfo) {
      SqlMethod sqlMethod = SqlMethod.UPDATE;
      String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
          sqlSet(true, true, tableInfo, true, Constants.ENTITY, Constants.ENTITY_DOT),
          sqlWhereEntityWrapper(true, tableInfo), sqlComment());
      SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
      return this.addUpdateMappedStatement(mapperClass, modelClass,
          SqlMethodConstants.UPDATE_ALL_COLUMN, sqlSource);
    }

    protected String sqlSet(boolean logic, boolean ew, TableInfo table, boolean judgeAliasNull,
        final String alias,
        final String prefix) {
      final String newPrefix = prefix == null ? StringPool.EMPTY : prefix;
      String sqlScript = table.getFieldList().stream()
          .filter(i -> {
            if (logic) {
              return !(table.isWithLogicDelete() && i.isLogicDelete());
            }
            return true;
          }).map(i -> i.getSqlSet(true, newPrefix)).filter(Objects::nonNull)
          .collect(joining(StringPool.NEWLINE));
      if (judgeAliasNull) {
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", alias), true);
      }
      if (ew) {
        sqlScript += StringPool.NEWLINE;
        sqlScript += SqlScriptUtils.convertIf(
            SqlScriptUtils.unSafeParam(Constants.U_WRAPPER_SQL_SET),
            String.format("%s != null and %s != null", Constants.WRAPPER,
                Constants.U_WRAPPER_SQL_SET), false);
      }
      sqlScript = SqlScriptUtils.convertSet(sqlScript);
      return sqlScript;
    }
  }
}

