package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.inner.bo.system.dic.category.GetSysDataDicCategoryBo;
import com.lframework.starter.web.inner.bo.system.dic.category.QuerySysDataDicCategoryBo;
import com.lframework.starter.web.inner.entity.SysDataDicCategory;
import com.lframework.starter.web.inner.service.system.SysDataDicCategoryService;
import com.lframework.starter.web.inner.vo.system.dic.category.CreateSysDataDicCategoryVo;
import com.lframework.starter.web.inner.vo.system.dic.category.QuerySysDataDicCategoryVo;
import com.lframework.starter.web.inner.vo.system.dic.category.UpdateSysDataDicCategoryVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Arrays;
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
 * 数据字典分类
 *
 * @author zmj
 */
@Tag(name = "数据字典分类")
@Validated
@RestController
@RequestMapping("/system/dic/category")
public class SysDataDicCategoryController extends DefaultBaseController {

  @Autowired
  private SysDataDicCategoryService sysDataDicCategoryService;

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
  @HasPermission(value = {"system:dic-category:*"}, requirePlatform = true)
  @GetMapping("/query")
  public InvokeResult<List<QuerySysDataDicCategoryBo>> query(@Valid QuerySysDataDicCategoryVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    List<SysDataDicCategory> datas = sysDataDicCategoryService.queryList();
    List<QuerySysDataDicCategoryBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysDataDicCategoryBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 根据ID查询
   */
  @Operation(summary = "根据ID查询")
  @Parameters({
      @Parameter(name = "id", description = "ID", required = true),
      @Parameter(name = "tenantId", description = "租户ID", required = true)
  })
  @HasPermission(value = {"system:dic-category:*"}, requirePlatform = true)
  @GetMapping
  public InvokeResult<GetSysDataDicCategoryBo> get(@NotBlank(message = "ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    SysDataDicCategory data = sysDataDicCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("数据字典分类不存在！");
    }

    GetSysDataDicCategoryBo result = new GetSysDataDicCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增数据字典分类
   */
  @Operation(summary = "新增数据字典分类")
  @HasPermission(value = {"system:dic-category:add"}, requirePlatform = true)
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysDataDicCategoryVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    sysDataDicCategoryService.create(vo);

    sysDataDicCategoryService.cleanCacheByKey("all");

    return InvokeResultBuilder.success();
  }

  /**
   * 修改数据字典分类
   */
  @Operation(summary = "修改数据字典分类")
  @HasPermission(value = {"system:dic-category:modify"}, requirePlatform = true)
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysDataDicCategoryVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    sysDataDicCategoryService.update(vo);

    sysDataDicCategoryService.cleanCacheByKeys(Arrays.asList("all", vo.getId()));

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "删除数据字典分类")
  @Parameters({
      @Parameter(name = "id", description = "ID", required = true),
      @Parameter(name = "tenantId", description = "租户ID", required = true)
  })
  @HasPermission(value = {"system:dic-category:delete"}, requirePlatform = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    sysDataDicCategoryService.deleteById(id);

    sysDataDicCategoryService.cleanCacheByKeys(Arrays.asList("all", id));

    return InvokeResultBuilder.success();
  }
}
