package com.lframework.gen.mappers;

import com.lframework.gen.dto.dataobj.GenGenerateInfoDto;
import com.lframework.gen.entity.GenGenerateInfo;
import com.lframework.starter.mybatis.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-12-08
 */
public interface GenGenerateInfoMapper extends BaseMapper<GenGenerateInfo> {

  /**
   * 根据数据对象ID查询
   *
   * @param dataObjId
   * @return
   */
  GenGenerateInfoDto getByDataObjId(String dataObjId);
}
