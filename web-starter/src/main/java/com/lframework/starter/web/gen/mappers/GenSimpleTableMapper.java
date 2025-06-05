package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.dto.simpledb.SimpleTableDto;
import com.lframework.starter.web.gen.entity.GenSimpleTable;
import com.lframework.starter.web.core.mapper.BaseMapper;
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
