package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysRoleCategory;
import com.lframework.starter.web.inner.vo.system.role.category.CreateSysRoleCategoryVo;
import com.lframework.starter.web.inner.vo.system.role.category.SysRoleCategorySelectorVo;
import com.lframework.starter.web.inner.vo.system.role.category.UpdateSysRoleCategoryVo;
import java.util.List;

/**
 * 角色分类 Service
 *
 * @author zmj
 */
public interface SysRoleCategoryService extends BaseMpService<SysRoleCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<SysRoleCategory> queryList();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<SysRoleCategory> selector(Integer pageIndex, Integer pageSize,
      SysRoleCategorySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysRoleCategory findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysRoleCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysRoleCategoryVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
