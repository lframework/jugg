package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenCustomPageCategory;
import com.lframework.starter.gen.vo.custom.page.category.CreateGenCustomPageCategoryVo;
import com.lframework.starter.gen.vo.custom.page.category.UpdateGenCustomPageCategoryVo;
import com.lframework.starter.mybatis.service.BaseMpService;
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
