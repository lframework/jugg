package com.lframework.starter.web.gen.impl;

import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.web.gen.mappers.DBMapper;
import com.lframework.starter.web.gen.mappers.SimpleDBMapper;
import com.lframework.starter.web.gen.service.SimpleDBService;
import com.lframework.starter.web.gen.service.SimpleTableColumnService;
import com.lframework.starter.web.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.starter.web.gen.vo.simpledb.SimpleTableSelectorVo;
import com.lframework.starter.web.gen.dto.simpledb.OriSimpleTableDto;
import com.lframework.starter.web.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleDBServiceImpl implements SimpleDBService {

  @Autowired
  private SimpleDBMapper simpleDBMapper;

  @Autowired
  private DBMapper dbMapper;

  @Autowired
  private SimpleTableColumnService simpleTableColumnService;

  @Override
  public String getCurrentDBName() {
    return dbMapper.getCurrentDBName();
  }

  @Override
  public PageResult<SimpleDBDto> selector(Integer pageIndex, Integer pageSize,
      SimpleTableSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SimpleDBDto> datas = simpleDBMapper.selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public OriSimpleTableDto getByTableName(String tableName) {
    OriSimpleTableDto table = simpleDBMapper.getByTableName(tableName);
    if (ObjectUtil.isNull(table)) {
      return table;
    }

    QuerySimpleTableColumnVo queryVo = new QuerySimpleTableColumnVo();
    queryVo.setTableName(tableName);

    List<GenSimpleTableColumn> columns = simpleTableColumnService.query(queryVo);
    table.setColumns(columns);

    return table;
  }

  @Override
  public List<SimpleDBDto> listByIds(List<String> tableNames) {
    return simpleDBMapper.listByIds(tableNames);
  }
}
