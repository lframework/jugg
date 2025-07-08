package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.core.utils.TenantUtil;
import com.lframework.starter.web.inner.bo.system.dept.SysDeptSelectorBo;
import com.lframework.starter.web.inner.bo.system.dic.SysDataDicSelectorBo;
import com.lframework.starter.web.inner.bo.system.dic.category.SysDataDicCategorySelectorBo;
import com.lframework.starter.web.inner.bo.system.menu.SysMenuSelectorBo;
import com.lframework.starter.web.inner.bo.system.notify.SysNotifyGroupSelectorBo;
import com.lframework.starter.web.inner.bo.system.open.SysOpenDomainSelectorBo;
import com.lframework.starter.web.inner.bo.system.role.SysRoleSelectorBo;
import com.lframework.starter.web.inner.bo.system.role.category.SysRoleCategorySelectorBo;
import com.lframework.starter.web.inner.bo.system.tenant.TenantSelectorBo;
import com.lframework.starter.web.inner.bo.system.user.SysUserSelectorBo;
import com.lframework.starter.web.inner.bo.system.user.group.SysUserGroupSelectorBo;
import com.lframework.starter.web.inner.entity.SysDataDic;
import com.lframework.starter.web.inner.entity.SysDataDicCategory;
import com.lframework.starter.web.inner.entity.SysDept;
import com.lframework.starter.web.inner.entity.SysMenu;
import com.lframework.starter.web.inner.entity.SysNotifyGroup;
import com.lframework.starter.web.inner.entity.SysOpenDomain;
import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.inner.entity.SysRoleCategory;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.inner.entity.SysUserGroup;
import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.inner.service.SysModuleTenantService;
import com.lframework.starter.web.inner.service.TenantService;
import com.lframework.starter.web.inner.service.system.SysDataDicCategoryService;
import com.lframework.starter.web.inner.service.system.SysDataDicService;
import com.lframework.starter.web.inner.service.system.SysDeptService;
import com.lframework.starter.web.inner.service.system.SysMenuService;
import com.lframework.starter.web.inner.service.system.SysNotifyGroupService;
import com.lframework.starter.web.inner.service.system.SysOpenDomainService;
import com.lframework.starter.web.inner.service.system.SysRoleCategoryService;
import com.lframework.starter.web.inner.service.system.SysRoleService;
import com.lframework.starter.web.inner.service.system.SysUserGroupService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import com.lframework.starter.web.inner.vo.system.dept.SysDeptSelectorVo;
import com.lframework.starter.web.inner.vo.system.dic.SysDataDicSelectorVo;
import com.lframework.starter.web.inner.vo.system.dic.category.SysDataDicCategorySelectorVo;
import com.lframework.starter.web.inner.vo.system.menu.SysMenuSelectorVo;
import com.lframework.starter.web.inner.vo.system.notify.SysNotifyGroupSelectorVo;
import com.lframework.starter.web.inner.vo.system.open.SysOpenDomainSelectorVo;
import com.lframework.starter.web.inner.vo.system.role.SysRoleSelectorVo;
import com.lframework.starter.web.inner.vo.system.role.category.SysRoleCategorySelectorVo;
import com.lframework.starter.web.inner.vo.system.tenant.TenantSelectorVo;
import com.lframework.starter.web.inner.vo.system.user.SysUserSelectorVo;
import com.lframework.starter.web.inner.vo.system.user.group.SysUserGroupSelectorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据选择器
 *
 * @author zmj
 */
@Api(tags = "数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class DefaultSysSelectorController extends DefaultBaseController {

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private SysMenuService sysMenuService;

  @Autowired
  private SysDeptService sysDeptService;

  @Autowired
  private SysRoleService sysRoleService;

  @Autowired
  private SysRoleCategoryService sysRoleCategoryService;

  @Autowired
  private SysDataDicCategoryService sysDataDicCategoryService;

  @Autowired
  private SysDataDicService sysDataDicService;

  @Autowired
  private SysOpenDomainService sysOpenDomainService;

  @Autowired
  private TenantService tenantService;

  @Autowired
  private SysModuleTenantService sysModuleTenantService;

  @Autowired
  private SysNotifyGroupService sysNotifyGroupService;

  @Autowired
  private SysUserGroupService sysUserGroupService;

  /**
   * 系统菜单
   */
  @ApiOperation("系统菜单")
  @GetMapping("/menu")
  public InvokeResult<List<SysMenuSelectorBo>> menu(@Valid SysMenuSelectorVo vo) {

    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(
          TenantContextHolder.getTenantId());
    }

    List<SysMenuSelectorBo> results = CollectionUtil.emptyList();
    List<SysMenu> datas = sysMenuService.selector(vo, moduleIds);
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysMenuSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("部门")
  @GetMapping("/dept")
  public InvokeResult<List<SysDeptSelectorBo>> dept(@Valid SysDeptSelectorVo vo) {

    List<SysDeptSelectorBo> results = CollectionUtil.emptyList();
    List<SysDept> datas = sysDeptService.selector(vo);
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysDeptSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("角色")
  @GetMapping("/role")
  public InvokeResult<PageResult<SysRoleSelectorBo>> role(@Valid SysRoleSelectorVo vo) {

    PageResult<SysRole> pageResult = sysRoleService.selector(getPageIndex(vo), getPageSize(vo), vo);
    List<SysRole> datas = pageResult.getDatas();
    List<SysRoleSelectorBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysRoleSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载角色
   */
  @ApiOperation("加载角色")
  @PostMapping("/role/load")
  public InvokeResult<List<SysRoleSelectorBo>> loadRole(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysRole> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysRoleService.findById(t)).filter(Objects::nonNull).collect(Collectors.toList());
    List<SysRoleSelectorBo> results = datas.stream().map(SysRoleSelectorBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("角色分类")
  @GetMapping("/role/category")
  public InvokeResult<PageResult<SysRoleCategorySelectorBo>> roleCategory(
      @Valid SysRoleCategorySelectorVo vo) {

    PageResult<SysRoleCategory> pageResult = sysRoleCategoryService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SysRoleCategory> datas = pageResult.getDatas();
    List<SysRoleCategorySelectorBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysRoleCategorySelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载角色分类
   */
  @ApiOperation("加载角色分类")
  @PostMapping("/role/category/load")
  public InvokeResult<List<SysRoleCategorySelectorBo>> loadRoleCategory(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysRoleCategory> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysRoleCategoryService.findById(t)).filter(Objects::nonNull)
        .collect(Collectors.toList());
    List<SysRoleCategorySelectorBo> results = datas.stream().map(SysRoleCategorySelectorBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("租户")
  @GetMapping("/tenant")
  public InvokeResult<PageResult<TenantSelectorBo>> tenant(@Valid TenantSelectorVo vo) {

    PageResult<Tenant> pageResult = tenantService.selector(getPageIndex(vo), getPageSize(vo), vo);
    List<Tenant> datas = pageResult.getDatas();
    List<TenantSelectorBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(TenantSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载租户
   */
  @ApiOperation("加载租户")
  @PostMapping("/tenant/load")
  public InvokeResult<List<TenantSelectorBo>> loadTenant(
      @RequestBody(required = false) List<Integer> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<Tenant> datas = ids.stream().filter(Objects::nonNull).map(t -> tenantService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<TenantSelectorBo> results = datas.stream().map(TenantSelectorBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("用户")
  @GetMapping("/user")
  public InvokeResult<PageResult<SysUserSelectorBo>> user(@Valid SysUserSelectorVo vo) {

    PageResult<SysUser> pageResult = sysUserService.selector(getPageIndex(vo), getPageSize(vo), vo);
    List<SysUser> datas = pageResult.getDatas();
    List<SysUserSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysUserSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载用户
   */
  @ApiOperation("加载用户")
  @PostMapping("/user/load")
  public InvokeResult<List<SysUserSelectorBo>> loadUser(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysUser> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysUserService.findById(t)).filter(Objects::nonNull).collect(Collectors.toList());
    List<SysUserSelectorBo> results = datas.stream().map(SysUserSelectorBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("数据字典分类")
  @GetMapping("/dic/category")
  public InvokeResult<PageResult<SysDataDicCategorySelectorBo>> dataDicCategory(
      @Valid SysDataDicCategorySelectorVo vo) {

    PageResult<SysDataDicCategory> pageResult = sysDataDicCategoryService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SysDataDicCategory> datas = pageResult.getDatas();
    List<SysDataDicCategorySelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysDataDicCategorySelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载数据字典分类
   */
  @ApiOperation("加载数据字典分类")
  @PostMapping("/dic/category/load")
  public InvokeResult<List<SysDataDicCategorySelectorBo>> loadDataDicCategory(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysDataDicCategory> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysDataDicCategoryService.findById(t)).filter(Objects::nonNull)
        .collect(Collectors.toList());
    List<SysDataDicCategorySelectorBo> results = datas.stream()
        .map(SysDataDicCategorySelectorBo::new).collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("数据字典")
  @GetMapping("/dic")
  public InvokeResult<PageResult<SysDataDicSelectorBo>> dataDic(@Valid SysDataDicSelectorVo vo) {

    PageResult<SysDataDic> pageResult = sysDataDicService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SysDataDic> datas = pageResult.getDatas();
    List<SysDataDicSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysDataDicSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载数据字典
   */
  @ApiOperation("加载数据字典")
  @PostMapping("/dic/load")
  public InvokeResult<List<SysDataDicSelectorBo>> loadDataDic(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysDataDic> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysDataDicService.findById(t)).filter(Objects::nonNull)
        .collect(Collectors.toList());
    List<SysDataDicSelectorBo> results = datas.stream().map(SysDataDicSelectorBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 开放域
   */
  @ApiOperation("开放域")
  @GetMapping("/openDomain")
  public InvokeResult<PageResult<SysOpenDomainSelectorBo>> openDomain(
      @Valid SysOpenDomainSelectorVo vo) {

    PageResult<SysOpenDomain> pageResult = sysOpenDomainService.selector(vo);

    List<SysOpenDomain> datas = pageResult.getDatas();
    List<SysOpenDomainSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysOpenDomainSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载开放域
   */
  @ApiOperation("加载开放域")
  @PostMapping("/openDomain/load")
  public InvokeResult<List<SysOpenDomainSelectorBo>> openDomain(
      @RequestBody(required = false) List<Integer> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysOpenDomain> datas = ids.stream().filter(Objects::nonNull)
        .map(t -> sysOpenDomainService.findById(t)).filter(Objects::nonNull)
        .collect(Collectors.toList());
    List<SysOpenDomainSelectorBo> results = datas.stream().map(SysOpenDomainSelectorBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("消息通知组")
  @GetMapping("/notify/group")
  public InvokeResult<PageResult<SysNotifyGroupSelectorBo>> notifyGroup(
      @Valid SysNotifyGroupSelectorVo vo) {

    PageResult<SysNotifyGroup> pageResult = sysNotifyGroupService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SysNotifyGroup> datas = pageResult.getDatas();
    List<SysNotifyGroupSelectorBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysNotifyGroupSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载消息通知组
   */
  @ApiOperation("加载消息通知组")
  @PostMapping("/notify/group/load")
  public InvokeResult<List<SysNotifyGroupSelectorBo>> loadNotifyGroup(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysNotifyGroup> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysNotifyGroupService.findById(t)).filter(Objects::nonNull)
        .collect(Collectors.toList());
    List<SysNotifyGroupSelectorBo> results = datas.stream().map(SysNotifyGroupSelectorBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("用户组")
  @GetMapping("/user/group")
  public InvokeResult<PageResult<SysUserGroupSelectorBo>> userGroup(
      @Valid SysUserGroupSelectorVo vo) {

    PageResult<SysUserGroup> pageResult = sysUserGroupService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SysUserGroup> datas = pageResult.getDatas();
    List<SysUserGroupSelectorBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysUserGroupSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载用户组
   */
  @ApiOperation("加载用户组")
  @PostMapping("/user/group/load")
  public InvokeResult<List<SysUserGroupSelectorBo>> loadUserGroup(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SysUserGroup> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> sysUserGroupService.findById(t)).filter(Objects::nonNull)
        .collect(Collectors.toList());
    List<SysUserGroupSelectorBo> results = datas.stream().map(SysUserGroupSelectorBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }
}
