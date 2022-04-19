package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.ISysPositionService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.position.CreateSysPositionVo;
import com.lframework.starter.mybatis.vo.system.position.QuerySysPositionVo;
import com.lframework.starter.mybatis.vo.system.position.UpdateSysPositionVo;
import com.lframework.starter.security.bo.system.position.GetSysPositionBo;
import com.lframework.starter.security.bo.system.position.QuerySysPositionBo;
import com.lframework.starter.security.controller.DefaultBaseController;
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
 * 岗位管理
 *
 * @author zmj
 */
@Api(tags = "岗位管理")
@Validated
@RestController
@RequestMapping("/system/position")
public class SysPositionController extends DefaultBaseController {

  @Autowired
  private ISysPositionService sysPositionService;

  /**
   * 岗位列表
   */
  @ApiOperation("岗位列表")
  @PreAuthorize("@permission.valid('system:position:query','system:position:add','system:position:modify')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysPositionBo>> query(@Valid QuerySysPositionVo vo) {

    PageResult<DefaultSysPositionDto> pageResult = sysPositionService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<DefaultSysPositionDto> datas = pageResult.getDatas();
    List<QuerySysPositionBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysPositionBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询岗位
   */
  @ApiOperation("查询岗位")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('system:position:query','system:position:add','system:position:modify')")
  @GetMapping
  public InvokeResult<GetSysPositionBo> get(@NotBlank(message = "ID不能为空！") String id) {

    DefaultSysPositionDto data = sysPositionService.getById(id);
    if (data == null) {
      throw new DefaultClientException("岗位不存在！");
    }

    GetSysPositionBo result = new GetSysPositionBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用岗位
   */
  @ApiOperation("批量停用岗位")
  @PreAuthorize("@permission.valid('system:position:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "岗位ID", required = true) @NotEmpty(message = "请选择需要停用的岗位！") @RequestBody List<String> ids) {

    sysPositionService.batchUnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用岗位
   */
  @ApiOperation("批量启用岗位")
  @PreAuthorize("@permission.valid('system:position:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "岗位ID", required = true) @NotEmpty(message = "请选择需要启用的岗位！") @RequestBody List<String> ids) {

    sysPositionService.batchEnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 新增岗位
   */
  @ApiOperation("新增岗位")
  @PreAuthorize("@permission.valid('system:position:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysPositionVo vo) {

    sysPositionService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改岗位
   */
  @ApiOperation("修改岗位")
  @PreAuthorize("@permission.valid('system:position:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysPositionVo vo) {

    sysPositionService.update(vo);

    return InvokeResultBuilder.success();
  }
}
