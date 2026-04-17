package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.inner.bo.system.role.GetSysRoleBo;
import com.lframework.starter.web.inner.bo.system.role.QuerySysRoleBo;
import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.inner.service.system.SysRoleService;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.vo.system.role.CreateSysRoleVo;
import com.lframework.starter.web.inner.vo.system.role.QuerySysRoleVo;
import com.lframework.starter.web.inner.vo.system.role.UpdateSysRoleVo;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理
 *
 * @author zmj
 */
@Tag(name = "角色管理")
@Validated
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends DefaultBaseController {

  @Autowired
  private SysRoleService sysRoleService;

  /**
   * 角色列表
   */
  @Operation(summary = "角色列表")
  @HasPermission({"system:role:query","system:role:add","system:role:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysRoleBo>> query(@Valid QuerySysRoleVo vo) {

    PageResult<SysRole> pageResult = sysRoleService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysRole> datas = pageResult.getDatas();
    List<QuerySysRoleBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysRoleBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询角色
   */
  @Operation(summary = "查询角色")
  @Parameter(name = "id", description = "ID", required = true)
  @HasPermission({"system:role:query","system:role:add","system:role:modify"})
  @GetMapping
  public InvokeResult<GetSysRoleBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysRole data = sysRoleService.findById(id);
    if (data == null) {
      throw new DefaultClientException("角色不存在！");
    }

    GetSysRoleBo result = new GetSysRoleBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 删除角色
   */
  @Operation(summary = "删除角色")
  @HasPermission({"system:role:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "角色ID", required = true) @NotEmpty(message = "角色ID不能为空！") String id) {

    sysRoleService.deleteById(id);

    sysRoleService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增角色
   */
  @Operation(summary = "新增角色")
  @HasPermission({"system:role:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysRoleVo vo) {

    sysRoleService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改角色
   */
  @Operation(summary = "修改角色")
  @HasPermission({"system:role:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysRoleVo vo) {

    sysRoleService.update(vo);

    sysRoleService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
