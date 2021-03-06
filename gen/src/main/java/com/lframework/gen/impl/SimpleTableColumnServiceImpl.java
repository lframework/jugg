package com.lframework.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.gen.converters.GenMysqlDataTypeConverter;
import com.lframework.gen.dto.simpledb.OriSimpleTableColumnDto;
import com.lframework.gen.dto.simpledb.SimpleTableColumnDto;
import com.lframework.gen.entity.GenSimpleTableColumn;
import com.lframework.gen.enums.GenConvertType;
import com.lframework.gen.enums.GenMySqlDataType;
import com.lframework.gen.mappers.GenSimpleTableColumnMapper;
import com.lframework.gen.service.IDataObjectColumnService;
import com.lframework.gen.service.ISimpleTableColumnService;
import com.lframework.gen.vo.dataobj.CreateDataObjectColumnVo;
import com.lframework.gen.vo.simpledb.CreateSimpleTableVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleTableColumnServiceImpl extends
    BaseMpServiceImpl<GenSimpleTableColumnMapper, GenSimpleTableColumn>
    implements ISimpleTableColumnService {

  @Autowired
  private IDataObjectColumnService dataObjectColumnService;

  @Autowired
  private GenMysqlDataTypeConverter genMysqlDataTypeConverter;

  @Override
  public List<SimpleTableColumnDto> getByTableId(String id) {

    return getBaseMapper().getByTableId(id);
  }

  @Transactional
  @Override
  public void deleteByTableId(String tableId) {

    Wrapper<GenSimpleTableColumn> wrapper = Wrappers.lambdaUpdate(GenSimpleTableColumn.class)
        .eq(GenSimpleTableColumn::getTableId, tableId);
    getBaseMapper().delete(wrapper);
  }

  @Transactional
  @Override
  public void create(@NonNull String tableId, @NonNull CreateSimpleTableVo vo) {

    List<OriSimpleTableColumnDto> columns = getBaseMapper().get(vo);
    if (!CollectionUtil.isEmpty(columns)) {
      List<GenSimpleTableColumn> columnList = columns.stream().map(t -> {
        GenSimpleTableColumn column = new GenSimpleTableColumn();
        column.setId(IdUtil.getId());
        column.setTableId(tableId);
        column.setColumnName(t.getColumnName());
        column.setIsNullable(t.getIsNullable());
        column.setIsKey(t.getIsKey());
        column.setColumnDefault(t.getColumnDefault());
        column.setOrdinalPosition(t.getOrdinalPosition());
        column.setColumnComment(t.getColumnComment());
        column.setDataType(t.getDataType().getDataType());

        return column;
      }).collect(Collectors.toList());

      for (GenSimpleTableColumn genSimpleTableColumn : columnList) {

        getBaseMapper().insert(genSimpleTableColumn);

        //??????DataObjectColumn
        CreateDataObjectColumnVo createDataObjectColumnVo = new CreateDataObjectColumnVo();
        createDataObjectColumnVo.setId(genSimpleTableColumn.getId());
        createDataObjectColumnVo.setDataObjId(vo.getDataObjId());
        //???????????????????????????????????????
        createDataObjectColumnVo.setName(genSimpleTableColumn.getColumnComment());
        //???????????????
        if (vo.getConvertType() == GenConvertType.UNDERLINE_TO_CAMEL.getCode().intValue()) {
          createDataObjectColumnVo.setPropertyName(
              StringUtil.toCamelCase(genSimpleTableColumn.getColumnName()));
        }

        createDataObjectColumnVo.setIsKey(genSimpleTableColumn.getIsKey());
        //JDBCType???JavaType
        GenMySqlDataType columnType = genMysqlDataTypeConverter.convert(
            genSimpleTableColumn.getDataType());
        if (columnType == null || columnType.getDataType() == null) {
          throw new DefaultClientException(
              "?????????" + genSimpleTableColumn.getColumnName() + "?????????????????????");
        }
        createDataObjectColumnVo.setDataType(columnType.getDataType().getCode());
        createDataObjectColumnVo.setColumnOrder(genSimpleTableColumn.getOrdinalPosition());

        dataObjectColumnService.create(createDataObjectColumnVo);
      }
    }
  }

  @Override
  public SimpleTableColumnDto findById(String id) {

    return getBaseMapper().findById(id);
  }
}
