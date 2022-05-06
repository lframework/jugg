package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.mybatis.service.system.ISysDeptService;
import com.lframework.starter.mybatis.vo.system.dept.CreateSysDeptVo;
import com.lframework.starter.mybatis.vo.system.dept.UpdateSysDeptVo;
import com.lframework.starter.security.bo.system.dept.GetSysDeptBo;
import com.lframework.starter.security.bo.system.dept.SysDeptTreeBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
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
 * 部门管理
 *
 * @author zmj
 */
@Api(tags = "部门管理")
@Validated
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends DefaultBaseController {

  @Autowired
  private ISysDeptService sysDeptService;

  /**
   * 部门树形菜单数据
   */
  @ApiOperation("部门树形菜单数据")
  @PreAuthorize("@permission.valid('system:dept:query','system:dept:add','system:dept:modify')")
  @GetMapping("/trees")
  public InvokeResult<List<SysDeptTreeBo>> trees() {

    List<DefaultSysDeptDto> datas = sysDeptService.selector();
    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }

    List<SysDeptTreeBo> results = datas.stream().map(SysDeptTreeBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询部门
   */
  @ApiOperation("部门详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('system:dept:query','system:dept:add','system:dept:modify')")
  @GetMapping
  public InvokeResult<GetSysDeptBo> get(@NotBlank(message = "ID不能为空！") String id) {

    DefaultSysDeptDto data = sysDeptService.findById(id);
    if (data == null) {
      throw new DefaultClientException("部门不存在！");
    }

    GetSysDeptBo result = new GetSysDeptBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用部门
   */
  @ApiOperation("批量停用部门")
  @PreAuthorize("@permission.valid('system:dept:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "部门ID", required = true) @NotEmpty(message = "请选择需要停用的部门！") @RequestBody List<String> ids) {

    sysDeptService.batchUnable(ids);

    for (String id : ids) {
      sysDeptService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用部门
   */
  @ApiOperation("批量启用部门")
  @PreAuthorize("@permission.valid('system:dept:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "部门ID", required = true) @NotEmpty(message = "请选择需要启用的部门！") @RequestBody List<String> ids) {

    sysDeptService.batchEnable(ids);

    for (String id : ids) {
      sysDeptService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 新增部门
   */
  @ApiOperation("新增部门")
  @PreAuthorize("@permission.valid('system:dept:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysDeptVo vo) {

    sysDeptService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改部门
   */
  @ApiOperation("修改部门")
  @PreAuthorize("@permission.valid('system:dept:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysDeptVo vo) {

    sysDeptService.update(vo);

    sysDeptService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
