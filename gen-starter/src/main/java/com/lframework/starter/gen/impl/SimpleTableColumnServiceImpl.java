package com.lframework.starter.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.converters.GenMysqlDataTypeConverter;
import com.lframework.starter.gen.converters.GenStringConverter;
import com.lframework.starter.gen.dto.simpledb.OriSimpleTableColumnDto;
import com.lframework.starter.gen.dto.simpledb.SimpleTableColumnDto;
import com.lframework.starter.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.gen.enums.GenConvertType;
import com.lframework.starter.gen.enums.GenMySqlDataType;
import com.lframework.starter.gen.mappers.GenSimpleTableColumnMapper;
import com.lframework.starter.gen.service.IDataObjectColumnService;
import com.lframework.starter.gen.service.ISimpleTableColumnService;
import com.lframework.starter.gen.vo.dataobj.CreateDataObjectColumnVo;
import com.lframework.starter.gen.vo.simpledb.CreateSimpleTableVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
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

        //创建DataObjectColumn
        CreateDataObjectColumnVo createDataObjectColumnVo = new CreateDataObjectColumnVo();
        createDataObjectColumnVo.setId(genSimpleTableColumn.getId());
        createDataObjectColumnVo.setDataObjId(vo.getDataObjId());
        //默认将列注释设置为显示名称
        createDataObjectColumnVo.setName(genSimpleTableColumn.getColumnComment());
        //属性名转换
        if (vo.getConvertType() == GenConvertType.UNDERLINE_TO_CAMEL.getCode().intValue()) {
          createDataObjectColumnVo.setPropertyName(GenStringConverter.convert(
              EnumUtil.getByCode(GenConvertType.class, vo.getConvertType()),
              genSimpleTableColumn.getColumnName()));
        }

        createDataObjectColumnVo.setIsKey(genSimpleTableColumn.getIsKey());
        //JDBCType转JavaType
        GenMySqlDataType columnType = genMysqlDataTypeConverter.convert(
            genSimpleTableColumn.getDataType());
        if (columnType == null || columnType.getDataType() == null) {
          throw new DefaultClientException(
              "字段：" + genSimpleTableColumn.getColumnName() + "类型暂不支持！");
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