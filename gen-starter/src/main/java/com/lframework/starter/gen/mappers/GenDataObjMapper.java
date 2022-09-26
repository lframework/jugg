package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.entity.GenDataObj;
import com.lframework.starter.gen.vo.data.obj.QueryGenDataObjVo;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据对象 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
public interface GenDataObjMapper extends BaseMapper<GenDataObj> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenDataObj> query(@Param("vo") QueryGenDataObjVo vo);

}
