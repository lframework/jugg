package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.vo.system.user.QuerySysUserVo;
import com.lframework.starter.mybatis.vo.system.user.SysUserSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
public interface DefaultSysUserMapper extends BaseMapper<DefaultSysUser> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<DefaultSysUser> query(@Param("vo") QuerySysUserVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysUser findById(String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<DefaultSysUser> selector(@Param("vo") SysUserSelectorVo vo);
}
