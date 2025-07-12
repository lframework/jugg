package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.gen.vo.custom.page.category.CreateGenCustomPageCategoryVo;
import com.lframework.starter.web.gen.vo.custom.page.category.GenCustomPageCategorySelectorVo;
import com.lframework.starter.web.gen.vo.custom.page.category.UpdateGenCustomPageCategoryVo;
import java.util.List;

/**
 * 自定义页面分类 Service
 *
 * @author zmj
 */
public interface GenCustomPageCategoryService extends BaseMpService<GenCustomPageCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenCustomPageCategory> queryList();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<GenCustomPageCategory> selector(Integer pageIndex, Integer pageSize,
      GenCustomPageCategorySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCustomPageCategory findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateGenCustomPageCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateGenCustomPageCategoryVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
