package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.inner.entity.SysDataDicCategory;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.vo.system.dic.category.CreateSysDataDicCategoryVo;
import com.lframework.starter.web.inner.vo.system.dic.category.SysDataDicCategorySelectorVo;
import com.lframework.starter.web.inner.vo.system.dic.category.UpdateSysDataDicCategoryVo;
import java.util.List;

/**
 * 数据字典分类 Service
 *
 * @author zmj
 */
public interface SysDataDicCategoryService extends BaseMpService<SysDataDicCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<SysDataDicCategory> queryList();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<SysDataDicCategory> selector(Integer pageIndex, Integer pageSize,
      SysDataDicCategorySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysDataDicCategory findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysDataDicCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysDataDicCategoryVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
