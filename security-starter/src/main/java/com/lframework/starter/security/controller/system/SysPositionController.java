package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.bo.system.position.GetSysPositionBo;
import com.lframework.starter.security.bo.system.position.QuerySysPositionBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.security.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.security.service.system.ISysPositionService;
import com.lframework.starter.security.vo.system.position.CreateSysPositionVo;
import com.lframework.starter.security.vo.system.position.QuerySysPositionVo;
import com.lframework.starter.security.vo.system.position.UpdateSysPositionVo;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@Validated
@RestController
@RequestMapping("/system/position")
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
public class SysPositionController extends DefaultBaseController {

  @Autowired
  private ISysPositionService sysPositionService;

  /**
   * 岗位列表
   */
  @PreAuthorize("@permission.valid('system:position:query','system:position:add','system:position:modify')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QuerySysPositionVo vo) {

    PageResult<DefaultSysPositionDto> pageResult = sysPositionService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<DefaultSysPositionDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QuerySysPositionBo> results = datas.stream().map(QuerySysPositionBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 查询岗位
   */
  @PreAuthorize("@permission.valid('system:position:query','system:position:add','system:position:modify')")
  @GetMapping
  public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

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
  @PreAuthorize("@permission.valid('system:position:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult batchUnable(
      @NotEmpty(message = "请选择需要停用的岗位！") @RequestBody List<String> ids) {

    sysPositionService.batchUnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用岗位
   */
  @PreAuthorize("@permission.valid('system:position:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult batchEnable(
      @NotEmpty(message = "请选择需要启用的岗位！") @RequestBody List<String> ids) {

    sysPositionService.batchEnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 新增岗位
   */
  @PreAuthorize("@permission.valid('system:position:add')")
  @PostMapping
  public InvokeResult create(@Valid CreateSysPositionVo vo) {

    sysPositionService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改岗位
   */
  @PreAuthorize("@permission.valid('system:position:modify')")
  @PutMapping
  public InvokeResult update(@Valid UpdateSysPositionVo vo) {

    sysPositionService.update(vo);

    return InvokeResultBuilder.success();
  }
}
