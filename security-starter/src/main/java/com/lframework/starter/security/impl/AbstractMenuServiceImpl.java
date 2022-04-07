package com.lframework.starter.security.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.components.IUserTokenResolver;
import com.lframework.starter.security.mappers.DefaultMenuMapper;
import com.lframework.starter.web.dto.MenuDto;
import com.lframework.starter.web.service.IMenuService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.RequestUtil;
import com.lframework.starter.web.utils.SpelUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 默认MenuService实现
 *
 * @author zmj
 */
public abstract class AbstractMenuServiceImpl implements IMenuService {

  @Autowired
  private DefaultMenuMapper defaultMenuMapper;

  @Autowired
  private IUserTokenResolver userTokenResolver;

  @Override
  public List<MenuDto> getMenuByUserId(String userId, boolean isAdmin) {

    List<MenuDto> menus = this.doGetMenus(userId, isAdmin);

    List<String> collectionMenuIds = this.doGetCollectMenuIds(userId);

    if (!CollectionUtil.isEmpty(menus)) {
      menus.stream().forEach(menu -> {
        menu.setPath(ApplicationUtil.resolvePlaceholders(menu.getPath()));
      });

      Map<String, Object> vars = getDefaultVars();
      menus.stream().filter(menu -> this.hasSpecExpression(menu.getPath())).forEach(menu -> {
        List<String> expressions = this.getAllExpressions(menu.getPath());
        if (!CollectionUtil.isEmpty(expressions)) {
          String oriPath = menu.getPath();
          for (String expression : expressions) {
            Object parsed = SpelUtil
                .parse(expression.replaceAll("\\{", "").replaceAll("}", ""), vars);
            oriPath = oriPath.replace(expression, parsed == null ? "" : String.valueOf(parsed));
          }

          menu.setPath(oriPath);
        }
      });

      if (!CollectionUtil.isEmpty(collectionMenuIds)) {
        menus.stream().forEach(menu -> {
          menu.setIsCollect(collectionMenuIds.contains(menu.getId()));
        });
      }
    }

    return menus;
  }

  @Override
  public Set<String> getPermissionsByUserId(String userId) {

    return defaultMenuMapper.getPermissionsByUserId(userId);
  }

  @Transactional
  @Override
  public void collect(String userId, String menuId) {

    if (StringUtil.isBlank(userId) || StringUtil.isBlank(menuId)) {
      return;
    }

    this.cancelCollect(userId, menuId);

    defaultMenuMapper.collectMenu(IdUtil.getId(), userId, menuId);
  }

  @Transactional
  @Override
  public void cancelCollect(String userId, String menuId) {

    if (StringUtil.isBlank(userId) || StringUtil.isBlank(menuId)) {
      return;
    }

    defaultMenuMapper.cancelCollectMenu(userId, menuId);
  }

  private List<String> getAllExpressions(String s) {

    if (!this.hasSpecExpression(s)) {
      return null;
    }

    List<String> results = new ArrayList<>();
    String[] arr = s.split("#\\{");
    for (int i = 1; i < arr.length; i++) {
      if (arr[i].indexOf("}") < 0) {
        continue;
      }
      results.add("#{" + arr[i].substring(0, arr[i].indexOf("}")) + "}");
    }

    return results;
  }

  private boolean hasSpecExpression(String s) {

    return RegUtil.isMatch(Pattern.compile("^.*#\\{.*}.*$"), s);
  }

  protected Map<String, Object> getDefaultVars() {

    Map<String, Object> vars = new HashMap<>();

    vars.put("_token", userTokenResolver.getToken(RequestUtil.getRequest()));

    return vars;
  }

  protected List<MenuDto> doGetMenus(String userId, boolean isAdmin) {

    return defaultMenuMapper.getMenuByUserId(userId, isAdmin);
  }

  protected List<String> doGetCollectMenuIds(String userId) {

    return defaultMenuMapper.getCollectMenuIds(userId);
  }
}
