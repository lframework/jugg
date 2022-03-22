package com.lframework.gen.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;

public interface DBMapper extends BaseMapper<Void> {

  /**
   * 查询当前数据库名称
   *
   * @return
   */
  String getCurrentDBName();
}
