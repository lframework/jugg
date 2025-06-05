package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.entity.GenDataEntity;
import com.lframework.starter.web.gen.vo.data.entity.GenDataEntitySelectorVo;
import com.lframework.starter.web.gen.vo.data.entity.QueryDataEntityVo;
import com.lframework.starter.web.core.mapper.BaseMapper;
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

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenDataEntity> selector(@Param("vo") GenDataEntitySelectorVo vo);
}
