package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.gen.dto.gen.GenCreateColumnConfigDto;
import com.lframework.starter.web.gen.entity.GenCreateColumnConfig;
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
public interface GenCreateColumnConfigMapper extends BaseMapper<GenCreateColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param ids
   * @return
   */
  List<GenCreateColumnConfigDto> getByIds(@Param("ids") List<String> ids);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCreateColumnConfigDto findById(String id);
}
