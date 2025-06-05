package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.entity.GenCustomListCategory;
import com.lframework.starter.web.gen.vo.custom.list.category.CreateGenCustomListCategoryVo;
import com.lframework.starter.web.gen.vo.custom.list.category.GenCustomListCategorySelectorVo;
import com.lframework.starter.web.gen.vo.custom.list.category.UpdateGenCustomListCategoryVo;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import java.util.List;

/**
 * 自定义列表分类 Service
 *
 * @author zmj
 */
public interface GenCustomListCategoryService extends BaseMpService<GenCustomListCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenCustomListCategory> queryList();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<GenCustomListCategory> selector(Integer pageIndex, Integer pageSize,
      GenCustomListCategorySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCustomListCategory findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateGenCustomListCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateGenCustomListCategoryVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
