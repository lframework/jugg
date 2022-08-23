package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.web.service.BaseService;
import java.util.List;

public interface ISimpleDBService extends BaseService {

  /**
   * 指定数据库名称查询数据库表名称
   *
   * @param dbName
   * @return
   */
  List<SimpleDBDto> getTables(String dbName);

  /**
   * 查询当前数据库表名称
   *
   * @return
   */
  List<SimpleDBDto> getCurrentTables();
}
