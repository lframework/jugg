package com.lframework.gen.impl;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.gen.dto.simpledb.SimpleTableColumnDto;
import com.lframework.gen.dto.simpledb.SimpleTableDto;
import com.lframework.gen.entity.GenSimpleTable;
import com.lframework.gen.enums.DataObjectGenStatus;
import com.lframework.gen.enums.GenConvertType;
import com.lframework.gen.mappers.GenSimpleTableMapper;
import com.lframework.gen.service.IDataObjectService;
import com.lframework.gen.service.ISimpleTableColumnService;
import com.lframework.gen.service.ISimpleTableService;
import com.lframework.gen.vo.simpledb.CreateSimpleTableVo;
import com.lframework.starter.web.utils.EnumUtil;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleTableServiceImpl implements ISimpleTableService {

  @Autowired
  private GenSimpleTableMapper genSimpleTableMapper;

  @Autowired
  private ISimpleTableColumnService simpleTableColumnService;

  @Autowired
  private IDataObjectService dataObjectService;

  @Override
  public SimpleTableDto getByDataObjId(String id) {

    SimpleTableDto table = genSimpleTableMapper.getByDataObjId(id);
    if (ObjectUtil.isNull(table)) {
      return table;
    }

    List<SimpleTableColumnDto> columns = simpleTableColumnService.getByTableId(table.getId());
    table.setColumns(columns);

    return table;
  }

  @Transactional
  @Override
  public String create(@NonNull CreateSimpleTableVo vo) {

    SimpleTableDto checkTable = this.getByDataObjId(vo.getDataObjId());
    if (!ObjectUtil.isNull(checkTable)) {
      throw new DefaultClientException("数据库表已设置，不允许重复设置！");
    }

    SimpleTableDto table = genSimpleTableMapper.get(vo.getTableSchema(), vo.getTableName());
    if (ObjectUtil.isNull(table)) {
      throw new DefaultClientException(
          "数据库表【" + vo.getTableSchema() + "." + vo.getTableName() + "】不存在！");
    }

    table.setId(vo.getDataObjId());

    GenSimpleTable simpleTable = new GenSimpleTable();
    simpleTable.setId(table.getId());
    simpleTable.setTableSchema(table.getTableSchema());
    simpleTable.setTableName(table.getTableName());
    simpleTable.setEngine(table.getEngine());
    simpleTable.setTableCollation(table.getTableCollation());
    simpleTable.setTableComment(table.getTableComment());
    simpleTable.setConvertType(EnumUtil.getByCode(GenConvertType.class, vo.getConvertType()));

    genSimpleTableMapper.insert(simpleTable);

    //创建列
    simpleTableColumnService.create(simpleTable.getId(), vo);

    //设置DataObj状态
    dataObjectService.setStatus(vo.getDataObjId(), DataObjectGenStatus.SET_TABLE);

    return simpleTable.getId();
  }

  @Transactional
  @Override
  public void deleteByDataObjId(String dataObjId) {

    SimpleTableDto table = this.getByDataObjId(dataObjId);
    if (ObjectUtil.isNull(table)) {
      return;
    }

    genSimpleTableMapper.deleteById(table.getId());

    this.simpleTableColumnService.deleteByTableId(table.getId());
  }
}
