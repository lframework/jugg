package com.lframework.starter.gen.builders;

import com.lframework.starter.gen.components.Table;
import com.lframework.starter.gen.components.TableColumn;
import com.lframework.starter.gen.enums.DataObjectType;

public interface TableBuilder {

  /**
   * 是否可以构建
   *
   * @param type
   * @return
   */
  boolean canBuild(DataObjectType type);

  /**
   * 构建Table
   *
   * @param dataObjId
   * @return
   */
  Table buildTable(String dataObjId);

  /**
   * 构建TableColumn
   *
   * @param id
   * @return
   */
  TableColumn buildTableColumn(String id);
}
