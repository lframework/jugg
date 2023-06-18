package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.vo.system.menu.SysMenuSelectorVo;
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
  List<DefaultSysMenu> query(@Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据角色ID查询已授权的菜单
   *
   * @param roleId
   * @return
   */
  List<DefaultSysMenu> getByRoleId(@Param("roleId") String roleId,
      @Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysMenu findById(String id);

  /**
   * 系统菜单选择器数据
   *
   * @return
   */
  List<DefaultSysMenu> selector(@Param("vo") SysMenuSelectorVo vo, @Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据ID查询子节点
   *
   * @param id
   * @return
   */
  List<DefaultSysMenu> getChildrenById(String id);
}
