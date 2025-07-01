package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.vo.simpledb.SimpleTableSelectorVo;
import com.lframework.starter.web.gen.dto.simpledb.OriSimpleTableDto;
import com.lframework.starter.web.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseService;
import java.util.List;

public interface SimpleDBService extends BaseService {

  /**
   * 查询当前数据库名称
   *
   * @return
   */
  String getCurrentDBName();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<SimpleDBDto> selector(Integer pageIndex, Integer pageSize, SimpleTableSelectorVo vo);

  /**
   * 根据表名查询
   *
   * @return
   */
  OriSimpleTableDto getByTableName(String tableName);

  /**
   * 批量查询
   *
   * @param tableNames
   * @return
   */
  List<SimpleDBDto> listByIds(List<String> tableNames);
}
