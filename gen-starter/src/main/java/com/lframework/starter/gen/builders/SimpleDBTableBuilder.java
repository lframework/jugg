package com.lframework.starter.gen.builders;

import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.gen.components.Table;
import com.lframework.starter.gen.components.TableColumn;
import com.lframework.starter.gen.dto.simpledb.SimpleTableDto;
import com.lframework.starter.gen.enums.GenType;
import com.lframework.starter.gen.service.ISimpleTableColumnService;
import com.lframework.starter.gen.service.ISimpleTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleDBTableBuilder implements TableBuilder {

  @Autowired
  private ISimpleTableService simpleTableService;

  @Autowired
  private ISimpleTableColumnService simpleTableColumnService;

  @Override
  public boolean canBuild(GenType type) {

    return type == GenType.SIMPLE_DB;
  }

  @Override
  public Table buildTable(String dataObjId) {

    SimpleTableDto simpleTable = simpleTableService.getByEntityId(dataObjId);
    if (simpleTable == null) {
      throw new DefaultSysException("SimpleTable不存在！");
    }
    return simpleTable;
  }

  @Override
  public TableColumn buildTableColumn(String id) {

    return simpleTableColumnService.findById(id);
  }
}
