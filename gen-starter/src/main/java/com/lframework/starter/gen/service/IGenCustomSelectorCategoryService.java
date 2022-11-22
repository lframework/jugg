package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenCustomSelectorCategory;
import com.lframework.starter.gen.vo.custom.selector.category.CreateGenCustomSelectorCategoryVo;
import com.lframework.starter.gen.vo.custom.selector.category.GenCustomSelectorCategorySelectorVo;
import com.lframework.starter.gen.vo.custom.selector.category.UpdateGenCustomSelectorCategoryVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

/**
 * 自定义选择器分类 Service
 *
 * @author zmj
 */
public interface IGenCustomSelectorCategoryService extends
    BaseMpService<GenCustomSelectorCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenCustomSelectorCategory> queryList();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<GenCustomSelectorCategory> selector(Integer pageIndex, Integer pageSize,
      GenCustomSelectorCategorySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCustomSelectorCategory findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateGenCustomSelectorCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateGenCustomSelectorCategoryVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
