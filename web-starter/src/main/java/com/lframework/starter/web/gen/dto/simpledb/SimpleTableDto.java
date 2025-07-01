package com.lframework.starter.web.gen.dto.simpledb;

import com.lframework.starter.web.gen.components.Table;
import com.lframework.starter.web.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.web.gen.enums.GenConvertType;
import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class SimpleTableDto implements BaseDto, Table, Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  /**
   * 数据表所属的数据库名
   */
  private String tableSchema;

  /**
   * 数据库表名
   */
  private String tableName;

  /**
   * 字段信息
   */
  private List<GenSimpleTableColumn> columns;

  /**
   * 数据库引擎
   */
  private String engine;

  /**
   * 字符校验编码集
   */
  private String tableCollation;

  /**
   * 备注
   */
  private String tableComment;

  /**
   * 转换方式
   */
  private GenConvertType convertType;

  @Override
  public String getSchema() {

    return this.tableSchema;
  }

  @Override
  public String getComment() {

    return this.tableComment;
  }
}
