package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.dto.gen.GenUpdateColumnConfigDto;
import com.lframework.starter.web.gen.entity.GenUpdateColumnConfig;
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
public interface GenUpdateColumnConfigMapper extends BaseMapper<GenUpdateColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param ids
   * @return
   */
  List<GenUpdateColumnConfigDto> getByIds(@Param("ids") List<String> ids);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenUpdateColumnConfigDto findById(String id);
}
