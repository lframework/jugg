package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.entity.GenDataObjCategory;
import com.lframework.starter.web.gen.vo.data.obj.category.CreateGenDataObjCategoryVo;
import com.lframework.starter.web.gen.vo.data.obj.category.GenDataObjCategorySelectorVo;
import com.lframework.starter.web.gen.vo.data.obj.category.UpdateGenDataObjCategoryVo;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import java.util.List;

/**
 * 数据对象分类 Service
 *
 * @author zmj
 */
public interface GenDataObjCategoryService extends BaseMpService<GenDataObjCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<GenDataObjCategory> queryList();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<GenDataObjCategory> selector(Integer pageIndex, Integer pageSize,
      GenDataObjCategorySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenDataObjCategory findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateGenDataObjCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateGenDataObjCategoryVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
