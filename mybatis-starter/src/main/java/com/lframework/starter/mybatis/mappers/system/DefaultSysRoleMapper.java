package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.mybatis.entity.DefaultSysRole;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.vo.system.role.QuerySysRoleVo;
import com.lframework.starter.mybatis.vo.system.role.SysRoleSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-02
 */
public interface DefaultSysRoleMapper extends BaseMapper<DefaultSysRole> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<DefaultSysRoleDto> query(@Param("vo") QuerySysRoleVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysRoleDto getById(String id);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<DefaultSysRoleDto> getByUserId(String userId);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<DefaultSysRoleDto> selector(@Param("vo") SysRoleSelectorVo vo);
}
