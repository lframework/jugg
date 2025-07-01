package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.vo.custom.selector.category.GenCustomSelectorCategorySelectorVo;
import com.lframework.starter.web.gen.entity.GenCustomSelectorCategory;
import com.lframework.starter.web.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 自定义选择器分类 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface GenCustomSelectorCategoryMapper extends BaseMapper<GenCustomSelectorCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenCustomSelectorCategory> query();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenCustomSelectorCategory> selector(@Param("vo") GenCustomSelectorCategorySelectorVo vo);
}
