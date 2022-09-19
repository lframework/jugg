package com.lframework.starter.security.controller.system;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.mybatis.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.mybatis.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.mybatis.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.mybatis.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.mybatis.entity.SysDataDicCategory;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.ISysDataDicCategoryService;
import com.lframework.starter.mybatis.service.system.ISysDeptService;
import com.lframework.starter.mybatis.service.system.ISysMenuService;
import com.lframework.starter.mybatis.service.system.ISysPositionService;
import com.lframework.starter.mybatis.service.system.ISysRoleService;
import com.lframework.starter.mybatis.service.system.ISysUserService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.dic.category.SysDataDicCategorySelectorVo;
import com.lframework.starter.mybatis.vo.system.menu.SysMenuSelectorVo;
import com.lframework.starter.mybatis.vo.system.position.SysPositionSelectorVo;
import com.lframework.starter.mybatis.vo.system.role.SysRoleSelectorVo;
import com.lframework.starter.mybatis.vo.system.user.SysUserSelectorVo;
import com.lframework.starter.security.bo.system.dept.SysDeptSelectorBo;
import com.lframework.starter.security.bo.system.dic.category.SysDataDicCategorySelectorBo;
import com.lframework.starter.security.bo.system.menu.SysMenuSelectorBo;
import com.lframework.starter.security.bo.system.position.SysPositionSelectorBo;
import com.lframework.starter.security.bo.system.role.SysRoleSelectorBo;
import com.lframework.starter.security.bo.system.user.SysUserSelectorBo;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
  private ISysUserService sysUserService;

  @Autowired
  private ISysMenuService sysMenuService;

  @Autowired
  private ISysDeptService sysDeptService;

  @Autowired
  private ISysPositionService sysPositionService;

  @Autowired
  private ISysRoleService sysRoleService;

  @Autowired
  private ISysDataDicCategoryService sysDataDicCategoryService;

  /**
   * 系统菜单
   */
  @ApiOperation("系统菜单")
  @GetMapping("/menu")
  public InvokeResult<List<SysMenuSelectorBo>> menu(@Valid SysMenuSelectorVo vo) {

    List<SysMenuSelectorBo> results = Collections.EMPTY_LIST;
    List<DefaultSysMenuDto> datas = sysMenuService.selector(vo);
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysMenuSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("部门")
  @GetMapping("/dept")
  public InvokeResult<List<SysDeptSelectorBo>> dept() {

    List<SysDeptSelectorBo> results = Collections.EMPTY_LIST;
    List<DefaultSysDeptDto> datas = sysDeptService.selector();
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysDeptSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("角色")
  @GetMapping("/role")
  public InvokeResult<PageResult<SysRoleSelectorBo>> role(@Valid SysRoleSelectorVo vo) {

    PageResult<DefaultSysRoleDto> pageResult = sysRoleService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<DefaultSysRoleDto> datas = pageResult.getDatas();
    List<SysRoleSelectorBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysRoleSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("用户")
  @GetMapping("/user")
  public InvokeResult<PageResult<SysUserSelectorBo>> user(@Valid SysUserSelectorVo vo) {

    PageResult<DefaultSysUserDto> pageResult = sysUserService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<DefaultSysUserDto> datas = pageResult.getDatas();
    List<SysUserSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysUserSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("岗位")
  @GetMapping("/position")
  public InvokeResult<PageResult<SysPositionSelectorBo>> position(@Valid SysPositionSelectorVo vo) {

    PageResult<DefaultSysPositionDto> pageResult = sysPositionService.selector(getPageIndex(vo),
        getPageSize(vo),
        vo);
    List<DefaultSysPositionDto> datas = pageResult.getDatas();
    List<SysPositionSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SysPositionSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("数据字典分类")
  @GetMapping("/dic/category")
  public InvokeResult<PageResult<SysDataDicCategorySelectorBo>> position(
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
}
