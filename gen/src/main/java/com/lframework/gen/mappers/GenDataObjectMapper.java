package com.lframework.gen.mappers;

import com.lframework.gen.dto.dataobj.DataObjectDto;
import com.lframework.gen.entity.GenDataObject;
import com.lframework.gen.vo.dataobj.QueryDataObjectVo;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据对象 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-05-17
 */
public interface GenDataObjectMapper extends BaseMapper<GenDataObject> {

  /**
   * 查询数据对象列表
   *
   * @param vo
   * @return
   */
  List<DataObjectDto> query(@Param("vo") QueryDataObjectVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DataObjectDto getById(@Param("id") String id);
}
