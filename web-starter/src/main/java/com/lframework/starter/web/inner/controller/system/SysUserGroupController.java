package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.bo.system.user.group.GetSysUserGroupBo;
import com.lframework.starter.web.inner.bo.system.user.group.QuerySysUserGroupBo;
import com.lframework.starter.web.inner.entity.SysUserGroup;
import com.lframework.starter.web.inner.service.system.SysUserGroupService;
import com.lframework.starter.web.inner.vo.system.user.group.CreateSysUserGroupVo;
import com.lframework.starter.web.inner.vo.system.user.group.QuerySysUserGroupVo;
import com.lframework.starter.web.inner.vo.system.user.group.UpdateSysUserGroupVo;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户组 Controller
 *
 * @author zmj
 */
@Tag(name = "用户组")
@Validated
@RestController
@RequestMapping("/sys/user/group")
public class SysUserGroupController extends DefaultBaseController {

  @Autowired
  private SysUserGroupService sysUserGroupService;

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
  @HasPermission({"system:user-group:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysUserGroupBo>> query(
      @Valid QuerySysUserGroupVo vo) {

    PageResult<SysUserGroup> pageResult = sysUserGroupService.query(
        getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysUserGroup> datas = pageResult.getDatas();
    List<QuerySysUserGroupBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysUserGroupBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @Operation(summary = "根据ID查询")
  @Parameter(name = "id", description = "ID", required = true)
  @HasPermission({"system:user-group:query"})
  @GetMapping("/detail")
  public InvokeResult<GetSysUserGroupBo> getDetail(
      @NotBlank(message = "id不能为空！") String id) {

    SysUserGroup data = sysUserGroupService.findById(id);
    if (data == null) {
      throw new DefaultClientException("消息通知组不存在！");
    }

    GetSysUserGroupBo result = new GetSysUserGroupBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @Operation(summary = "新增")
  @HasPermission({"system:user-group:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateSysUserGroupVo vo) {

    sysUserGroupService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @Operation(summary = "修改")
  @HasPermission({"system:user-group:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateSysUserGroupVo vo) {

    sysUserGroupService.update(vo);

    sysUserGroupService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "根据ID删除")
  @HasPermission({"system:user-group:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "ID不能为空！") String id) {

    sysUserGroupService.deleteById(id);
    sysUserGroupService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
