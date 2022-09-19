package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.entity.GenDataEntityCategory;
import com.lframework.starter.gen.vo.data.entity.category.GenDataEntityCategorySelectorVo;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据实体分类 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface GenDataEntityCategoryMapper extends BaseMapper<GenDataEntityCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenDataEntityCategory> query();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenDataEntityCategory> selector(@Param("vo") GenDataEntityCategorySelectorVo vo);
}
