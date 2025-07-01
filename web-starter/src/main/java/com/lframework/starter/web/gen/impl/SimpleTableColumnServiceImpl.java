package com.lframework.starter.web.gen.impl;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.web.gen.mappers.GenSimpleTableColumnMapper;
import com.lframework.starter.web.gen.service.SimpleDBService;
import com.lframework.starter.web.gen.service.SimpleTableColumnService;
import com.lframework.starter.web.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.starter.web.gen.dto.simpledb.OriSimpleTableColumnDto;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleTableColumnServiceImpl extends
    BaseMpServiceImpl<GenSimpleTableColumnMapper, GenSimpleTableColumn>
    implements SimpleTableColumnService {

  @Autowired
  private SimpleDBService simpleDBService;

  @Override
  public List<GenSimpleTableColumn> query(QuerySimpleTableColumnVo vo) {
    if (StringUtil.isBlank(vo.getTableSchema())) {
      vo.setTableSchema(simpleDBService.getCurrentDBName());
    }
    List<OriSimpleTableColumnDto> columns = getBaseMapper().query(vo);
    if (!CollectionUtil.isEmpty(columns)) {

      return columns.stream().map(t -> {
        GenSimpleTableColumn column = new GenSimpleTableColumn();
        column.setId(IdUtil.getId());
        column.setColumnName(t.getColumnName());
        column.setIsNullable(t.getIsNullable());
        column.setIsKey(t.getIsKey());
        column.setColumnDefault(t.getColumnDefault());
        column.setOrdinalPosition(t.getOrdinalPosition());
        column.setColumnComment(t.getColumnComment());
        column.setDataType(t.getDataType().getDataType());
        column.setLen(t.getLen());
        column.setDecimals(t.getDecimals());

        return column;
      }).collect(Collectors.toList());
    }

    return null;
  }
}
