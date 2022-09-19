package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.dto.simpledb.SimpleTableDto;
import com.lframework.starter.gen.entity.GenSimpleTable;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-05-28
 */
public interface GenSimpleTableMapper extends BaseMapper<GenSimpleTable> {

  /**
   * 根据数据实体ID查询
   *
   * @param id
   * @return
   */
  SimpleTableDto getByEntityId(@Param("id") String id);
}
