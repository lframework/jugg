package com.lframework.gen.mappers;

import com.lframework.gen.dto.dataobj.GenUpdateColumnConfigDto;
import com.lframework.gen.entity.GenUpdateColumnConfig;
import com.lframework.starter.mybatis.mapper.BaseMapper;
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
  GenUpdateColumnConfigDto getById(String id);
}
