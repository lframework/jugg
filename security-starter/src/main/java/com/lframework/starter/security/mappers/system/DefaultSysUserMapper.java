package com.lframework.starter.security.mappers.system;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.security.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.security.entity.DefaultSysUser;
import com.lframework.starter.security.vo.system.user.QuerySysUserVo;
import com.lframework.starter.security.vo.system.user.SysUserSelectorVo;
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
  List<DefaultSysUserDto> query(@Param("vo") QuerySysUserVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysUserDto getById(String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<DefaultSysUserDto> selector(@Param("vo") SysUserSelectorVo vo);
}
