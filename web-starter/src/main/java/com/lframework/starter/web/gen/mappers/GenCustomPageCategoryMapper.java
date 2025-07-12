package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.gen.vo.custom.page.category.GenCustomPageCategorySelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenCustomPageCategory> selector(@Param("vo") GenCustomPageCategorySelectorVo vo);
}
