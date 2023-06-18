package com.lframework.starter.mybatis.service;

import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.web.dto.MenuDto;
import java.util.List;
import java.util.Set;

/**
 * 菜单Service
 *
 * @author zmj
 */
public interface MenuService extends BaseMpService<DefaultSysMenu> {

  /**
   * 根据用户ID查询菜单
   *
   * @param userId
   * @param isAdmin 是否为管理员
   * @return
   */
  List<MenuDto> getMenuByUserId(String userId, boolean isAdmin, List<Integer> moduleIds);

  /**
   * 根据用户ID查询权限
   *
   * @param userId
   * @return
   */
  Set<String> getPermissionsByUserId(String userId, boolean isAdmin, List<Integer> moduleIds);

  /**
   * 根据用户ID查询角色权限
   * @param userId
   * @return
   */
  Set<String> getRolePermissionByUserId(String userId);

  /**
   * 收藏菜单
   *
   * @param userId
   * @param menuId
   */
  void collect(String userId, String menuId);

  /**
   * 取消收藏菜单
   *
   * @param userId
   * @param menuId
   */
  void cancelCollect(String userId, String menuId);
}
