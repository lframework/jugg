package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.inner.bo.system.role.QueryRoleMenuBo;
import com.lframework.starter.web.inner.entity.SysMenu;
import com.lframework.starter.web.inner.service.SysModuleTenantService;
import com.lframework.starter.web.inner.service.system.SysMenuService;
import com.lframework.starter.web.inner.service.system.SysRoleMenuService;
import com.lframework.starter.web.inner.vo.system.role.SysRoleMenuSettingVo;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.utils.TenantUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.lframework.starter.web.core.annotations.security.HasPermission;
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
@Tag(name = "角色授权")
@Validated
@RestController
@RequestMapping("/system/role/menu")
public class SysRoleMenuController extends DefaultBaseController {

  @Autowired
  private SysMenuService sysMenuService;

  @Autowired
  private SysRoleMenuService sysRoleMenuService;

  @Autowired
  private SysModuleTenantService sysModuleTenantService;

  /**
   * 查询角色菜单列表
   */
  @Operation(summary = "查询角色菜单列表")
  @Parameter(name = "roleId", description = "角色ID")
  @HasPermission({"system:role:permission"})
  @GetMapping("/menus")
  public InvokeResult<List<QueryRoleMenuBo>> menus(String roleId) {

    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(TenantContextHolder.getTenantId());
    }

    List<QueryRoleMenuBo> results = CollectionUtil.emptyList();
    //查询所有菜单
    List<SysMenu> allMenu = sysMenuService.queryList(moduleIds);
    if (!CollectionUtil.isEmpty(allMenu)) {
      results = allMenu.stream().map(QueryRoleMenuBo::new).collect(Collectors.toList());

      if (!StringUtil.isBlank(roleId)) {
        List<SysMenu> menus = sysMenuService.getByRoleId(roleId, moduleIds);
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
  @Operation(summary = "授权角色菜单")
  @HasPermission({"system:role:permission"})
  @PostMapping("/setting")
  public InvokeResult<Void> setting(@Valid @RequestBody SysRoleMenuSettingVo vo) {

    sysRoleMenuService.setting(vo);
    return InvokeResultBuilder.success();
  }
}
