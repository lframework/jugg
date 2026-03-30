package com.lframework.starter.web.inner.support.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.inner.dto.system.MenuDto;
import com.lframework.starter.web.inner.entity.SysUserMenuSort;
import com.lframework.starter.web.inner.vo.auth.SaveUserMenuSortVo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户菜单排序支持类
 *
 * @author lframework@163.com
 */
public class UserMenuSortSupport {

  /**
   * 根据用户排序偏好重排菜单
   */
  public List<MenuDto> sortMenus(List<MenuDto> menus, List<SysUserMenuSort> records) {

    if (CollectionUtil.isEmpty(menus) || CollectionUtil.isEmpty(records)) {
      return menus;
    }

    Map<String, List<MenuDto>> childrenMap = new LinkedHashMap<>();
    for (MenuDto menu : menus) {
      childrenMap.computeIfAbsent(normalizeParentId(menu.getParentId()), key -> new ArrayList<>())
          .add(menu);
    }

    Map<String, Map<String, Integer>> sortMap = new HashMap<>();
    for (SysUserMenuSort record : records) {
      sortMap.computeIfAbsent(normalizeParentId(record.getParentId()), key -> new HashMap<>())
          .put(record.getMenuId(), record.getSortNo());
    }

    List<MenuDto> results = new ArrayList<>();
    appendSortedMenus(null, childrenMap, sortMap, results);
    return results;
  }

  /**
   * 根据排序快照生成用户偏好记录
   */
  public List<SysUserMenuSort> buildSortRecords(String userId, List<MenuDto> visibleMenus,
      SaveUserMenuSortVo vo) {

    List<MenuDto> menus = visibleMenus == null ? Collections.emptyList() : visibleMenus;
    List<SaveUserMenuSortVo.MenuSortNodeVo> nodes =
        vo == null || vo.getMenus() == null ? Collections.emptyList() : vo.getMenus();

    Map<String, MenuDto> menuMap = new HashMap<>();
    for (MenuDto menu : menus) {
      menuMap.put(menu.getId(), menu);
    }

    List<SysUserMenuSort> results = new ArrayList<>();
    Set<String> visited = new HashSet<>();
    appendSortRecords(userId, null, nodes, menuMap, visited, results);

    if (visited.size() != menuMap.size()) {
      throw new DefaultClientException("菜单排序快照不完整！");
    }

    return results;
  }

  private void appendSortedMenus(String parentId, Map<String, List<MenuDto>> childrenMap,
      Map<String, Map<String, Integer>> sortMap, List<MenuDto> results) {

    List<MenuDto> siblings = childrenMap.get(normalizeParentId(parentId));
    if (CollectionUtil.isEmpty(siblings)) {
      return;
    }

    List<MenuDto> sortedSiblings = new ArrayList<>(siblings);
    Map<String, Integer> defaultIndexMap = new HashMap<>();
    for (int i = 0; i < siblings.size(); i++) {
      defaultIndexMap.put(siblings.get(i).getId(), i);
    }

    Map<String, Integer> currentSortMap =
        sortMap.getOrDefault(normalizeParentId(parentId), Collections.emptyMap());
    sortedSiblings.sort(menuComparator(currentSortMap, defaultIndexMap));

    for (MenuDto sibling : sortedSiblings) {
      results.add(sibling);
      appendSortedMenus(sibling.getId(), childrenMap, sortMap, results);
    }
  }

  private Comparator<MenuDto> menuComparator(Map<String, Integer> sortMap,
      Map<String, Integer> defaultIndexMap) {

    return (o1, o2) -> {
      Integer o1SortNo = sortMap.get(o1.getId());
      Integer o2SortNo = sortMap.get(o2.getId());

      if (o1SortNo != null && o2SortNo != null) {
        int compareResult = o1SortNo.compareTo(o2SortNo);
        if (compareResult != 0) {
          return compareResult;
        }
      } else if (o1SortNo != null) {
        return -1;
      } else if (o2SortNo != null) {
        return 1;
      }

      return defaultIndexMap.get(o1.getId()).compareTo(defaultIndexMap.get(o2.getId()));
    };
  }

  private void appendSortRecords(String userId, String parentId,
      List<SaveUserMenuSortVo.MenuSortNodeVo> nodes, Map<String, MenuDto> menuMap,
      Set<String> visited, List<SysUserMenuSort> results) {

    if (CollectionUtil.isEmpty(nodes)) {
      return;
    }

    for (int i = 0; i < nodes.size(); i++) {
      SaveUserMenuSortVo.MenuSortNodeVo node = nodes.get(i);

      MenuDto menu = menuMap.get(node.getId());
      if (menu == null) {
        throw new DefaultClientException("菜单不存在或无权限！");
      }

      if (!visited.add(node.getId())) {
        throw new DefaultClientException("菜单排序快照中存在重复菜单！");
      }

      if (!StringUtil.equals(normalizeParentId(parentId), normalizeParentId(menu.getParentId()))) {
        throw new DefaultClientException("不允许跨父级调整菜单顺序！");
      }

      SysUserMenuSort record = new SysUserMenuSort();
      record.setUserId(userId);
      record.setMenuId(node.getId());
      record.setParentId(menu.getParentId());
      record.setSortNo(i + 1);
      results.add(record);

      appendSortRecords(userId, node.getId(), node.getChildren(), menuMap, visited, results);
    }
  }

  private String normalizeParentId(String parentId) {

    return StringUtil.isBlank(parentId) ? "__ROOT__" : parentId;
  }
}
