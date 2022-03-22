package com.lframework.gen.builders;

import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.gen.components.Table;
import com.lframework.gen.components.TableColumn;
import com.lframework.gen.dto.simpledb.SimpleTableDto;
import com.lframework.gen.enums.DataObjectType;
import com.lframework.gen.service.ISimpleTableColumnService;
import com.lframework.gen.service.ISimpleTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleDBTableBuilder implements TableBuilder {

  @Autowired
  private ISimpleTableService simpleTableService;

  @Autowired
  private ISimpleTableColumnService simpleTableColumnService;

  @Override
  public boolean canBuild(DataObjectType type) {

    return type == DataObjectType.SIMPLE_DB;
  }

  @Override
  public Table buildTable(String dataObjId) {

    SimpleTableDto simpleTable = simpleTableService.getByDataObjId(dataObjId);
    if (simpleTable == null) {
      throw new DefaultSysException("SimpleTable不存在！");
    }
    return simpleTable;
  }

  @Override
  public TableColumn buildTableColumn(String id) {

    return simpleTableColumnService.getById(id);
  }
}
