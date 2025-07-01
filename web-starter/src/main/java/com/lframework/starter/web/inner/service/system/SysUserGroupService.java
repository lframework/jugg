package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysUserGroup;
import com.lframework.starter.web.inner.vo.system.user.group.CreateSysUserGroupVo;
import com.lframework.starter.web.inner.vo.system.user.group.QuerySysUserGroupVo;
import com.lframework.starter.web.inner.vo.system.user.group.SysUserGroupSelectorVo;
import com.lframework.starter.web.inner.vo.system.user.group.UpdateSysUserGroupVo;
import java.util.List;

public interface SysUserGroupService extends BaseMpService<SysUserGroup> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysUserGroup> query(Integer pageIndex, Integer pageSize,
      QuerySysUserGroupVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysUserGroup> query(QuerySysUserGroupVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysUserGroup findById(String id);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<SysUserGroup> selector(Integer pageIndex, Integer pageSize,
      SysUserGroupSelectorVo vo);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysUserGroupVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysUserGroupVo vo);
}
