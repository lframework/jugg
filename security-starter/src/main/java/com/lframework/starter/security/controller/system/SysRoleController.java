package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.ISysRoleService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.role.CreateSysRoleVo;
import com.lframework.starter.mybatis.vo.system.role.QuerySysRoleVo;
import com.lframework.starter.mybatis.vo.system.role.UpdateSysRoleVo;
import com.lframework.starter.security.bo.system.role.GetSysRoleBo;
import com.lframework.starter.security.bo.system.role.QuerySysRoleBo;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理
 *
 * @author zmj
 */
@Api(tags = "角色管理")
@Validated
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends DefaultBaseController {

  @Autowired
  private ISysRoleService sysRoleService;

  /**
   * 角色列表
   */
  @ApiOperation("角色列表")
  @PreAuthorize("@permission.valid('system:role:query','system:role:add','system:role:modify')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysRoleBo>> query(@Valid QuerySysRoleVo vo) {

    PageResult<DefaultSysRoleDto> pageResult = sysRoleService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<DefaultSysRoleDto> datas = pageResult.getDatas();
    List<QuerySysRoleBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysRoleBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询角色
   */
  @ApiOperation("查询角色")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('system:role:query','system:role:add','system:role:modify')")
  @GetMapping
  public InvokeResult<GetSysRoleBo> get(@NotBlank(message = "ID不能为空！") String id) {

    DefaultSysRoleDto data = sysRoleService.findById(id);
    if (data == null) {
      throw new DefaultClientException("角色不存在！");
    }

    GetSysRoleBo result = new GetSysRoleBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用角色
   */
  @ApiOperation("批量停用角色")
  @PreAuthorize("@permission.valid('system:role:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "角色ID", required = true) @NotEmpty(message = "请选择需要停用的角色！") @RequestBody List<String> ids) {

    sysRoleService.batchUnable(ids);

    for (String id : ids) {
      sysRoleService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用角色
   */
  @ApiOperation("批量启用角色")
  @PreAuthorize("@permission.valid('system:role:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "角色ID", required = true) @NotEmpty(message = "请选择需要启用的角色！") @RequestBody List<String> ids) {

    sysRoleService.batchEnable(ids);

    for (String id : ids) {
      sysRoleService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 新增角色
   */
  @ApiOperation("新增角色")
  @PreAuthorize("@permission.valid('system:role:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysRoleVo vo) {

    sysRoleService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改角色
   */
  @ApiOperation("修改角色")
  @PreAuthorize("@permission.valid('system:role:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysRoleVo vo) {

    sysRoleService.update(vo);

    sysRoleService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
