package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.entity.GenCustomListCategory;
import com.lframework.starter.gen.vo.custom.list.category.GenCustomListCategorySelectorVo;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 自定义列表分类 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface GenCustomListCategoryMapper extends BaseMapper<GenCustomListCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenCustomListCategory> query();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenCustomListCategory> selector(@Param("vo") GenCustomListCategorySelectorVo vo);
}
