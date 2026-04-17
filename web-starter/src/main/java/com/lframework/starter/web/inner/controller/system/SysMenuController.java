package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.starter.web.core.utils.TenantUtil;
import com.lframework.starter.web.inner.bo.system.menu.GetSysMenuBo;
import com.lframework.starter.web.inner.bo.system.menu.QuerySysMenuBo;
import com.lframework.starter.web.inner.entity.SysMenu;
import com.lframework.starter.web.inner.enums.system.SysMenuDisplay;
import com.lframework.starter.web.inner.service.SysModuleTenantService;
import com.lframework.starter.web.inner.service.system.SysMenuService;
import com.lframework.starter.web.inner.vo.system.menu.CreateSysMenuVo;
import com.lframework.starter.web.inner.vo.system.menu.QuerySysMenuVo;
import com.lframework.starter.web.inner.vo.system.menu.UpdateSysMenuVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统菜单管理
 *
 * @author zmj
 */
@Tag(name = "系统菜单管理")
@Validated
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends DefaultBaseController {

  @Autowired
  private SysMenuService sysMenuService;

  @Autowired
  private SysModuleTenantService sysModuleTenantService;

  /**
   * 系统菜单列表
   */
  @Operation(summary = "系统菜单列表")
  @HasPermission(value = {"system:menu:query", "system:menu:add"}, requirePlatform = true)
  @GetMapping("/query")
  public InvokeResult<List<QuerySysMenuBo>> query(@Valid QuerySysMenuVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(
          vo.getTenantId());
    }

    List<QuerySysMenuBo> results = CollectionUtil.emptyList();
    List<SysMenu> datas = sysMenuService.queryList(moduleIds);
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QuerySysMenuBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 新增系统菜单
   */
  @Operation(summary = "新增系统菜单")
  @HasPermission(value = {"system:menu:add"}, requirePlatform = true)
  @PostMapping
  public InvokeResult<Void> add(@Valid CreateSysMenuVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    this.validVo(vo);

    sysMenuService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 查看系统菜单
   */
  @Operation(summary = "查看系统菜单")
  @Parameters({
      @Parameter(name = "id", description = "ID", required = true),
      @Parameter(name = "tenantId", description = "租户ID", required = true)
  })
  @HasPermission({"system:menu:query", "system:menu:add", "system:menu:modify"})
  @GetMapping
  public InvokeResult<GetSysMenuBo> get(@NotBlank(message = "ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    SysMenu data = sysMenuService.findById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("菜单不存在！");
    }

    return InvokeResultBuilder.success(new GetSysMenuBo(data));
  }

  /**
   * 修改系统菜单
   */
  @Operation(summary = "修改系统菜单")
  @HasPermission(value = {"system:menu:modify"}, requirePlatform = true)
  @PutMapping
  public InvokeResult<Void> modify(@Valid UpdateSysMenuVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    this.validVo(vo);

    sysMenuService.update(vo);

    sysMenuService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "根据ID删除")
  @Parameters({
      @Parameter(name = "id", description = "ID", required = true),
      @Parameter(name = "tenantId", description = "租户ID", required = true)
  })
  @HasPermission(value = {"system:menu:delete"}, requirePlatform = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    sysMenuService.deleteById(id);

    sysMenuService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 启用
   */
  @Operation(summary = "启用")
  @Parameters({
      @Parameter(name = "id", description = "ID", required = true),
      @Parameter(name = "tenantId", description = "租户ID", required = true)
  })
  @HasPermission(value = {"system:menu:modify"}, requirePlatform = true)
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @Parameter(description = "菜单ID", required = true) @NotEmpty(message = "菜单ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    sysMenuService.enable(id);

    sysMenuService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 停用
   */
  @Operation(summary = "停用")
  @Parameters({
      @Parameter(name = "id", description = "ID", required = true),
      @Parameter(name = "tenantId", description = "租户ID", required = true)
  })
  @HasPermission(value = {"system:menu:modify"}, requirePlatform = true)
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @Parameter(description = "菜单ID", required = true) @NotEmpty(message = "菜单ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    sysMenuService.unable(id);

    sysMenuService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  private void validVo(CreateSysMenuVo vo) {

    SysMenuDisplay sysMenuDisplay = EnumUtil.getByCode(SysMenuDisplay.class, vo.getDisplay());

    if (sysMenuDisplay == SysMenuDisplay.CATALOG || sysMenuDisplay == SysMenuDisplay.FUNCTION) {
      if (StringUtil.isBlank(vo.getName())) {
        throw new InputErrorException("请输入路由名称！");
      }

      if (StringUtil.isBlank(vo.getPath())) {
        throw new InputErrorException("请输入路由路径！");
      }

      if (ObjectUtil.isNull(vo.getHidden())) {
        throw new InputErrorException("请选择是否隐藏！");
      }

      if (sysMenuDisplay == SysMenuDisplay.FUNCTION) {
        if (vo.getComponentType() == null) {
          throw new InputErrorException("请选择组件类型！");
        }
        if (StringUtil.isBlank(vo.getComponent())) {
          throw new InputErrorException("请输入组件！");
        }
        if (ObjectUtil.isNull(vo.getNoCache())) {
          throw new InputErrorException("请选择是否不缓存！");
        }

        if (!StringUtil.isBlank(vo.getParentId())) {
          SysMenu parentMenu = sysMenuService.findById(vo.getParentId());

          if (parentMenu.getDisplay() != SysMenuDisplay.CATALOG) {
            throw new InputErrorException(
                "当菜单类型是“" + SysMenuDisplay.FUNCTION.getDesc() + "”时，父级菜单类型必须是“"
                    + SysMenuDisplay.CATALOG.getDesc() + "”！");
          }
        }
      }
    } else if (sysMenuDisplay == SysMenuDisplay.PERMISSION) {
      if (StringUtil.isBlank(vo.getParentId())) {
        throw new InputErrorException(
            "当菜单类型是“" + SysMenuDisplay.PERMISSION.getDesc() + "”时，父级菜单不能为空！");
      }

      SysMenu parentMenu = sysMenuService.findById(vo.getParentId());
      if (ObjectUtil.isNull(parentMenu)) {
        throw new InputErrorException(
            "当菜单类型是“" + SysMenuDisplay.PERMISSION.getDesc() + "”时，父级菜单不能为空！");
      }

      if (parentMenu.getDisplay() != SysMenuDisplay.FUNCTION) {
        throw new InputErrorException(
            "当菜单类型是“" + SysMenuDisplay.PERMISSION.getDesc() + "”时，父级菜单类型必须是“"
                + SysMenuDisplay.FUNCTION.getDesc() + "”！");
      }
      if (StringUtil.isBlank(vo.getPermission())) {
        throw new InputErrorException("请输入权限！");
      }
    }
  }
}
