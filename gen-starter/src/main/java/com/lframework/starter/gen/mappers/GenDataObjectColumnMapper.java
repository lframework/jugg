package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.starter.gen.entity.GenDataObjectColumn;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;

public interface GenDataObjectColumnMapper extends BaseMapper<GenDataObjectColumn> {

  /**
   * 根据数据对象ID查询
   *
   * @param dataObjId
   * @return
   */
  List<GenDataObjectColumnDto> getByDataObjId(String dataObjId);
}
