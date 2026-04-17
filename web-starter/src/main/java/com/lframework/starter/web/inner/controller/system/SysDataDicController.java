package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.bo.system.dic.GetSysDataDicBo;
import com.lframework.starter.web.inner.bo.system.dic.QuerySysDataDicBo;
import com.lframework.starter.web.inner.entity.SysDataDic;
import com.lframework.starter.web.inner.service.system.SysDataDicService;
import com.lframework.starter.web.inner.vo.system.dic.CreateSysDataDicVo;
import com.lframework.starter.web.inner.vo.system.dic.QuerySysDataDicVo;
import com.lframework.starter.web.inner.vo.system.dic.UpdateSysDataDicVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典
 *
 * @author zmj
 */
@Tag(name = "数据字典")
@Validated
@RestController
@RequestMapping("/system/dic")
public class SysDataDicController extends DefaultBaseController {

  @Autowired
  private SysDataDicService sysDataDicService;

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
  @HasPermission(value = {"system:dic:*"}, requirePlatform = true)
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysDataDicBo>> query(@Valid QuerySysDataDicVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    PageResult<SysDataDic> pageResult = sysDataDicService.query(getPageIndex(vo), getPageSize(vo),
        vo);
    List<SysDataDic> datas = pageResult.getDatas();
    List<QuerySysDataDicBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysDataDicBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @Operation(summary = "根据ID查询")
  @Parameters({
      @Parameter(name = "id", description = "ID", required = true),
      @Parameter(name = "tenantId", description = "租户ID", required = true)
  })
  @HasPermission(value = {"system:dic:*"}, requirePlatform = true)
  @GetMapping
  public InvokeResult<GetSysDataDicBo> get(@NotBlank(message = "ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    SysDataDic data = sysDataDicService.findById(id);
    if (data == null) {
      throw new DefaultClientException("数据字典不存在！");
    }

    GetSysDataDicBo result = new GetSysDataDicBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增数据字典
   */
  @Operation(summary = "新增数据字典")
  @HasPermission(value = {"system:dic:add"}, requirePlatform = true)
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysDataDicVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    sysDataDicService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改数据字典
   */
  @Operation(summary = "修改数据字典")
  @HasPermission(value = {"system:dic:modify"}, requirePlatform = true)
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysDataDicVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    sysDataDicService.update(vo);

    sysDataDicService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 删除数据字典
   */
  @Operation(summary = "删除数据字典")
  @Parameters({
      @Parameter(name = "id", description = "ID", required = true),
      @Parameter(name = "tenantId", description = "租户ID", required = true)
  })
  @HasPermission(value = {"system:dic:delete"}, requirePlatform = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    sysDataDicService.deleteById(id);

    sysDataDicService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
