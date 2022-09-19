package com.lframework.starter.gen.impl;

import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.gen.dto.simpledb.SimpleTableDto;
import com.lframework.starter.gen.entity.GenSimpleTable;
import com.lframework.starter.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.gen.mappers.GenSimpleTableMapper;
import com.lframework.starter.gen.service.ISimpleTableColumnService;
import com.lframework.starter.gen.service.ISimpleTableService;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleTableServiceImpl extends BaseMpServiceImpl<GenSimpleTableMapper, GenSimpleTable>
    implements ISimpleTableService {

  @Autowired
  private ISimpleTableColumnService simpleTableColumnService;

  @Override
  public SimpleTableDto getByEntityId(String id) {

    SimpleTableDto table = getBaseMapper().getByEntityId(id);
    if (ObjectUtil.isNull(table)) {
      return table;
    }

    List<GenSimpleTableColumn> columns = simpleTableColumnService.getByTableId(table.getId());
    table.setColumns(columns);

    return table;
  }

  @Transactional
  @Override
  public void deleteByEntityId(String entityId) {

    SimpleTableDto table = this.getByEntityId(entityId);
    if (ObjectUtil.isNull(table)) {
      return;
    }

    getBaseMapper().deleteById(table.getId());

    this.simpleTableColumnService.deleteByTableId(table.getId());
  }
}
