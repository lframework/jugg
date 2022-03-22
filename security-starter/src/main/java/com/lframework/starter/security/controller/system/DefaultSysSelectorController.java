package com.lframework.starter.security.controller.system;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.bo.system.dept.SysDeptSelectorBo;
import com.lframework.starter.security.bo.system.menu.SysMenuSelectorBo;
import com.lframework.starter.security.bo.system.position.SysPositionSelectorBo;
import com.lframework.starter.security.bo.system.role.SysRoleSelectorBo;
import com.lframework.starter.security.bo.system.user.SysUserSelectorBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.security.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.security.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.security.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.security.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.security.service.system.ISysDeptService;
import com.lframework.starter.security.service.system.ISysMenuService;
import com.lframework.starter.security.service.system.ISysPositionService;
import com.lframework.starter.security.service.system.ISysRoleService;
import com.lframework.starter.security.service.system.ISysUserService;
import com.lframework.starter.security.vo.system.menu.SysMenuSelectorVo;
import com.lframework.starter.security.vo.system.position.SysPositionSelectorVo;
import com.lframework.starter.security.vo.system.role.SysRoleSelectorVo;
import com.lframework.starter.security.vo.system.user.SysUserSelectorVo;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据选择器
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/selector")
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
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

  /**
   * 系统菜单
   */
  @GetMapping("/menu")
  public InvokeResult menu(@Valid SysMenuSelectorVo vo) {

    List<SysMenuSelectorBo> results = Collections.EMPTY_LIST;
    List<DefaultSysMenuDto> datas = sysMenuService.selector(vo);
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysMenuSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @GetMapping("/dept")
  public InvokeResult dept() {

    List<SysDeptSelectorBo> results = Collections.EMPTY_LIST;
    List<DefaultSysDeptDto> datas = sysDeptService.selector();
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SysDeptSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @GetMapping("/role")
  public InvokeResult role(@Valid SysRoleSelectorVo vo) {

    PageResult<DefaultSysRoleDto> pageResult = sysRoleService
        .selector(getPageIndex(vo), getPageSize(vo), vo);
    List<DefaultSysRoleDto> datas = pageResult.getDatas();
    if (CollectionUtil.isNotEmpty(datas)) {
      List<SysRoleSelectorBo> results = datas.stream().map(SysRoleSelectorBo::new)
          .collect(Collectors.toList());
      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 用户
   */
  @GetMapping("/user")
  public InvokeResult user(@Valid SysUserSelectorVo vo) {

    PageResult<DefaultSysUserDto> pageResult = sysUserService
        .selector(getPageIndex(vo), getPageSize(vo), vo);
    List<DefaultSysUserDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<SysUserSelectorBo> results = datas.stream().map(SysUserSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  @GetMapping("/position")
  public InvokeResult position(@Valid SysPositionSelectorVo vo) {

    PageResult<DefaultSysPositionDto> pageResult = sysPositionService
        .selector(getPageIndex(vo), getPageSize(vo), vo);
    List<DefaultSysPositionDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<SysPositionSelectorBo> results = datas.stream().map(SysPositionSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }
}
