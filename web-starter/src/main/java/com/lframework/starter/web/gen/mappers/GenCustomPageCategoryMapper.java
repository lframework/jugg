package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 自定义页面分类 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface GenCustomPageCategoryMapper extends BaseMapper<GenCustomPageCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenCustomPageCategory> query();
}
