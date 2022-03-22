package com.lframework.starter.security.controller.system;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.bo.system.role.QueryRoleMenuBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.security.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.security.service.system.ISysMenuService;
import com.lframework.starter.security.service.system.ISysRoleMenuService;
import com.lframework.starter.security.vo.system.role.SysRoleMenuSettingVo;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色授权
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/system/role/menu")
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
public class SysRoleMenuController extends DefaultBaseController {

  @Autowired
  private ISysMenuService sysMenuService;

  @Autowired
  private ISysRoleMenuService sysRoleMenuService;

  /**
   * 查询角色菜单列表
   */
  @PreAuthorize("@permission.valid('system:role:permission')")
  @GetMapping("/menus")
  public InvokeResult menus(String roleId) {

    List<QueryRoleMenuBo> results = Collections.EMPTY_LIST;
    //查询所有菜单
    List<DefaultSysMenuDto> allMenu = sysMenuService.query();
    if (!CollectionUtil.isEmpty(allMenu)) {
      results = allMenu.stream().map(QueryRoleMenuBo::new).collect(Collectors.toList());

      if (!StringUtil.isBlank(roleId)) {
        List<DefaultSysMenuDto> menus = sysMenuService.getByRoleId(roleId);
        if (!CollectionUtil.isEmpty(menus)) {
          //当角色的菜单存在时，设置已选择属性
          for (QueryRoleMenuBo result : results) {
            result.setSelected(
                menus.stream().anyMatch(t -> StringUtil.equals(t.getId(), result.getId())));
          }
        }
      }
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 授权角色菜单
   */
  @PreAuthorize("@permission.valid('system:role:permission')")
  @PostMapping("/setting")
  public InvokeResult setting(@Valid @RequestBody SysRoleMenuSettingVo vo) {

    sysRoleMenuService.setting(vo);
    return InvokeResultBuilder.success();
  }
}
