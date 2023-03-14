package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.user.CreateSysUserVo;
import com.lframework.starter.mybatis.vo.system.user.QuerySysUserVo;
import com.lframework.starter.mybatis.vo.system.user.RegistUserVo;
import com.lframework.starter.mybatis.vo.system.user.SysUserSelectorVo;
import com.lframework.starter.mybatis.vo.system.user.UpdateSysUserVo;
import java.util.List;

public interface SysUserService extends BaseMpService<DefaultSysUser> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<DefaultSysUser> query(Integer pageIndex, Integer pageSize, QuerySysUserVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<DefaultSysUser> query(QuerySysUserVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysUser findById(String id);

  /**
   * 批量启用
   *
   * @param ids
   */
  void batchEnable(List<String> ids);

  /**
   * 批量停用
   *
   * @param ids
   */
  void batchUnable(List<String> ids);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysUserVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysUserVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<DefaultSysUser> selector(Integer pageIndex, Integer pageSize, SysUserSelectorVo vo);

  /**
   * 注册
   *
   * @param vo
   */
  void regist(RegistUserVo vo);
}
