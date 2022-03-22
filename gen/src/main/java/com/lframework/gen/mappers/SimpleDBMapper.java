package com.lframework.gen.mappers;

import com.lframework.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SimpleDBMapper extends BaseMapper {

  /**
   * 指定数据库名称查询数据库表名称
   *
   * @param dbName
   * @return
   */
  List<SimpleDBDto> getTables(@Param("dbName") String dbName);
}
