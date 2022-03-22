package com.lframework.starter.security.mappers.system;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.security.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.security.entity.DefaultSysMenu;
import com.lframework.starter.security.vo.system.menu.SysMenuSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-05-10
 */
public interface DefaultSysMenuMapper extends BaseMapper<DefaultSysMenu> {

  /**
   * 系统菜单列表
   *
   * @return
   */
  List<DefaultSysMenuDto> query();

  /**
   * 根据角色ID查询已授权的菜单
   *
   * @param roleId
   * @return
   */
  List<DefaultSysMenuDto> getByRoleId(String roleId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysMenuDto getById(String id);

  /**
   * 系统菜单选择器数据
   *
   * @return
   */
  List<DefaultSysMenuDto> selector(@Param("vo") SysMenuSelectorVo vo);

  /**
   * 根据ID查询子节点
   *
   * @param id
   * @return
   */
  List<DefaultSysMenuDto> getChildrenById(String id);
}
