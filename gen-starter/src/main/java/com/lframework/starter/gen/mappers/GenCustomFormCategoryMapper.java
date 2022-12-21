package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.entity.GenCustomFormCategory;
import com.lframework.starter.gen.vo.custom.form.category.GenCustomFormCategorySelectorVo;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 自定义表单分类 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface GenCustomFormCategoryMapper extends BaseMapper<GenCustomFormCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenCustomFormCategory> query();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenCustomFormCategory> selector(@Param("vo") GenCustomFormCategorySelectorVo vo);
}
