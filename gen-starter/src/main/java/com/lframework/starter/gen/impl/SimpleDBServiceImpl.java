package com.lframework.starter.gen.impl;

import com.github.pagehelper.PageInfo;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.gen.dto.simpledb.OriSimpleTableDto;
import com.lframework.starter.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.gen.mappers.DBMapper;
import com.lframework.starter.gen.mappers.SimpleDBMapper;
import com.lframework.starter.gen.service.ISimpleDBService;
import com.lframework.starter.gen.service.ISimpleTableColumnService;
import com.lframework.starter.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.starter.gen.vo.simpledb.SimpleTableSelectorVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleDBServiceImpl implements ISimpleDBService {

  @Autowired
  private SimpleDBMapper simpleDBMapper;

  @Autowired
  private DBMapper dbMapper;

  @Autowired
  private ISimpleTableColumnService simpleTableColumnService;

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
}
