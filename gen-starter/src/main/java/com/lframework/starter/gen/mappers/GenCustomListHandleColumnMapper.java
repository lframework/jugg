package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.entity.GenCustomListHandleColumn;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenCustomListHandleColumnMapper extends BaseMapper<GenCustomListHandleColumn> {

  /**
   * 根据自定义列表ID查询
   *
   * @param customListId
   * @return
   */
  List<GenCustomListHandleColumn> getByCustomListId(@Param("customListId") String customListId);
}
