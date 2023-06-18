package com.lframework.starter.mybatis.mappers;

import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.web.dto.MenuDto;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

/**
 * 默认MenuMapper
 *
 * @author zmj
 */
public interface DefaultMenuMapper extends BaseMapper<DefaultSysMenu> {

  /**
   * 根据用户ID查询菜单
   *
   * @param userId
   * @param isAdmin 是否为管理员
   * @return
   */
  List<MenuDto> getMenuByUserId(@Param("userId") String userId, @Param("isAdmin") boolean isAdmin,
      @Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据用户ID查询收藏的菜单
   *
   * @param userId
   * @return
   */
  List<String> getCollectMenuIds(@Param("userId") String userId);

  /**
   * 根据用户ID查询权限
   *
   * @param userId
   * @return
   */
  Set<String> getPermissionsByUserId(@Param("userId") String userId,
      @Param("isAdmin") boolean isAdmin,
      @Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据用户ID查询角色权限
   *
   * @param userId
   * @return
   */
  Set<String> getRolePermissionByUserId(@Param("userId") String userId);

  /**
   * 收藏菜单
   *
   * @param id
   * @param userId
   * @param menuId
   */
  void collectMenu(@Param("id") String id, @Param("userId") String userId,
      @Param("menuId") String menuId);

  /**
   * 取消收藏菜单
   *
   * @param userId
   * @param menuId
   */
  void cancelCollectMenu(@Param("userId") String userId, @Param("menuId") String menuId);
}
