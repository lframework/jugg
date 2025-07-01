package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.entity.GenCustomListToolbar;
import com.lframework.starter.web.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenCustomListToolbarMapper extends BaseMapper<GenCustomListToolbar> {

  /**
   * 根据自定义列表ID查询
   *
   * @param customListId
   * @return
   */
  List<GenCustomListToolbar> getByCustomListId(@Param("customListId") String customListId);
}
