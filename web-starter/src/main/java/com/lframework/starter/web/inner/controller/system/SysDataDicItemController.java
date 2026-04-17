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
import com.lframework.starter.web.inner.bo.system.dic.item.GetSysDataDicItemBo;
import com.lframework.starter.web.inner.bo.system.dic.item.QuerySysDataDicItemBo;
import com.lframework.starter.web.inner.bo.system.dic.item.SysDataDicItemBo;
import com.lframework.starter.web.inner.entity.SysDataDic;
import com.lframework.starter.web.inner.entity.SysDataDicItem;
import com.lframework.starter.web.inner.service.system.SysDataDicItemService;
import com.lframework.starter.web.inner.service.system.SysDataDicService;
import com.lframework.starter.web.inner.vo.system.dic.item.CreateSysDataDicItemVo;
import com.lframework.starter.web.inner.vo.system.dic.item.QuerySysDataDicItemVo;
import com.lframework.starter.web.inner.vo.system.dic.item.UpdateSysDataDicItemVo;
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
 * 数据字典值
 *
 * @author zmj
 */
@Tag(name = "数据字典值")
@Validated
@RestController
@RequestMapping("/system/dic/item")
public class SysDataDicItemController extends DefaultBaseController {

  @Autowired
  private SysDataDicItemService sysDataDicItemService;

  @Autowired
  private SysDataDicService sysDataDicService;

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
  @HasPermission(value = {"system:dic-item:*"}, requirePlatform = true)
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysDataDicItemBo>> query(@Valid QuerySysDataDicItemVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    PageResult<SysDataDicItem> pageResult = sysDataDicItemService.query(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SysDataDicItem> datas = pageResult.getDatas();
    List<QuerySysDataDicItemBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysDataDicItemBo::new).collect(Collectors.toList());
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
  @HasPermission(value = {"system:dic-item:*"}, requirePlatform = true)
  @GetMapping
  public InvokeResult<GetSysDataDicItemBo> get(@NotBlank(message = "ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    SysDataDicItem data = sysDataDicItemService.findById(id);
    if (data == null) {
      throw new DefaultClientException("数据字典值不存在！");
    }

    GetSysDataDicItemBo result = new GetSysDataDicItemBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据字典编号查询
   */
  @Operation(summary = "根据字典编号查询")
  @Parameter(name = "code", description = "字典编号", required = true)
  @GetMapping("/bydic")
  public InvokeResult<List<SysDataDicItemBo>> getByDicCode(
      @NotBlank(message = "字典编号不能为空！") String code) {
    List<SysDataDicItem> datas = sysDataDicItemService.findByDicCode(code);

    List<SysDataDicItemBo> results = datas.stream().map(SysDataDicItemBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 新增数据字典值
   */
  @Operation(summary = "新增数据字典值")
  @HasPermission(value = {"system:dic-item:add"}, requirePlatform = true)
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysDataDicItemVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    sysDataDicItemService.create(vo);

    SysDataDic dic = sysDataDicService.findById(vo.getDicId());
    sysDataDicItemService.cleanCacheByKey(dic.getCode());

    return InvokeResultBuilder.success();
  }

  /**
   * 修改数据字典值
   */
  @Operation(summary = "修改数据字典值")
  @HasPermission(value = {"system:dic-item:modify"}, requirePlatform = true)
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysDataDicItemVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    SysDataDicItem item = sysDataDicItemService.findById(vo.getId());

    sysDataDicItemService.update(vo);

    SysDataDic dic = sysDataDicService.findById(item.getDicId());
    sysDataDicItemService.cleanCacheByKey(dic.getCode());

    sysDataDicItemService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 删除数据字典值
   */
  @Operation(summary = "删除数据字典值")
  @Parameters({
      @Parameter(name = "id", description = "ID", required = true),
      @Parameter(name = "tenantId", description = "租户ID", required = true)
  })
  @HasPermission(value = {"system:dic-item:delete"}, requirePlatform = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    SysDataDicItem item = sysDataDicItemService.findById(id);

    sysDataDicItemService.deleteById(id);

    SysDataDic dic = sysDataDicService.findById(item.getDicId());
    sysDataDicItemService.cleanCacheByKey(dic.getCode());

    sysDataDicItemService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
