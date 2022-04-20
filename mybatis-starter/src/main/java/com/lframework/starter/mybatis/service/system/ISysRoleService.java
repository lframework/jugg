package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.mybatis.entity.DefaultSysRole;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.role.CreateSysRoleVo;
import com.lframework.starter.mybatis.vo.system.role.QuerySysRoleVo;
import com.lframework.starter.mybatis.vo.system.role.SysRoleSelectorVo;
import com.lframework.starter.mybatis.vo.system.role.UpdateSysRoleVo;
import java.util.Collection;
import java.util.List;

public interface ISysRoleService extends BaseMpService<DefaultSysRole> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<DefaultSysRoleDto> query(Integer pageIndex, Integer pageSize, QuerySysRoleVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<DefaultSysRoleDto> query(QuerySysRoleVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysRoleDto findById(String id);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<DefaultSysRoleDto> selector(Integer pageIndex, Integer pageSize, SysRoleSelectorVo vo);

  /**
   * 根据ID停用
   *
   * @param ids
   */
  void batchUnable(Collection<String> ids);

  /**
   * 根据ID启用
   *
   * @param ids
   */
  void batchEnable(Collection<String> ids);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysRoleVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysRoleVo vo);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<DefaultSysRoleDto> getByUserId(String userId);
}
