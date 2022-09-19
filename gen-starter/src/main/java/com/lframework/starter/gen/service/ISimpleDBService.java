package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.simpledb.OriSimpleTableDto;
import com.lframework.starter.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.gen.vo.simpledb.SimpleTableSelectorVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;

public interface ISimpleDBService extends BaseService {

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
}
