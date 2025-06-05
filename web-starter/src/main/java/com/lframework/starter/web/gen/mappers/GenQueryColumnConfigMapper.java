package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.dto.gen.GenQueryColumnConfigDto;
import com.lframework.starter.web.gen.entity.GenQueryColumnConfig;
import com.lframework.starter.web.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-12-10
 */
public interface GenQueryColumnConfigMapper extends BaseMapper<GenQueryColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param ids
   * @return
   */
  List<GenQueryColumnConfigDto> getByIds(@Param("ids") List<String> ids);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenQueryColumnConfigDto findById(String id);
}
