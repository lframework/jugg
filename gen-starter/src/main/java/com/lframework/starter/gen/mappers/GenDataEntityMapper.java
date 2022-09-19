package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.entity.GenDataEntity;
import com.lframework.starter.gen.vo.data.entity.QueryDataEntityVo;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据实体 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
public interface GenDataEntityMapper extends BaseMapper<GenDataEntity> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenDataEntity> query(@Param("vo") QueryDataEntityVo vo);
}
