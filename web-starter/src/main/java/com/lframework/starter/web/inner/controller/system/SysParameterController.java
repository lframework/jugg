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
import com.lframework.starter.web.inner.bo.system.parameter.GetSysParameterBo;
import com.lframework.starter.web.inner.bo.system.parameter.QuerySysParameterBo;
import com.lframework.starter.web.inner.entity.SysParameter;
import com.lframework.starter.web.inner.service.system.SysParameterService;
import com.lframework.starter.web.inner.vo.system.parameter.CreateSysParameterVo;
import com.lframework.starter.web.inner.vo.system.parameter.QuerySysParameterVo;
import com.lframework.starter.web.inner.vo.system.parameter.UpdateSysParameterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数 Controller
 *
 * @author zmj
 */
@Api(tags = "系统参数")
@Validated
@RestController
@RequestMapping("/system/parameter")
public class SysParameterController extends DefaultBaseController {

  @Autowired
  private SysParameterService sysParameterService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission(value = {"system:parameter:query"}, requirePlatform = true)
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysParameterBo>> query(@Valid QuerySysParameterVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    PageResult<SysParameter> pageResult = sysParameterService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysParameter> datas = pageResult.getDatas();
    List<QuerySysParameterBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysParameterBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true),
      @ApiImplicitParam(value = "租户ID", name = "tenantId", paramType = "query", required = true)
  })
  @HasPermission(value = {"system:parameter:query"}, requirePlatform = true)
  @GetMapping
  public InvokeResult<GetSysParameterBo> get(@NotNull(message = "id不能为空！") Long id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    SysParameter data = sysParameterService.getById(id);
    if (data == null) {
      throw new DefaultClientException("系统参数不存在！");
    }

    GetSysParameterBo result = new GetSysParameterBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission(value = {"system:parameter:add"}, requirePlatform = true)
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysParameterVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    sysParameterService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission(value = {"system:parameter:modify"}, requirePlatform = true)
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysParameterVo vo) {

    TenantContextHolder.setTenantId(vo.getTenantId());

    sysParameterService.update(vo);

    sysParameterService.cleanCacheByKey(vo.getId());

    SysParameter data = sysParameterService.findById(vo.getId());
    sysParameterService.cleanCacheByKey(data.getPmKey());

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @ApiOperation("根据ID删除")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true),
      @ApiImplicitParam(value = "租户ID", name = "tenantId", paramType = "query", required = true)
  })
  @HasPermission(value = {"system:parameter:delete"}, requirePlatform = true)
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotNull(message = "id不能为空！") Long id,
      @NotNull(message = "租户ID不能为空！") Integer tenantId) {

    TenantContextHolder.setTenantId(tenantId);

    SysParameter data = sysParameterService.findById(id);

    sysParameterService.deleteById(id);

    sysParameterService.cleanCacheByKey(id);

    sysParameterService.cleanCacheByKey(data.getPmKey());

    return InvokeResultBuilder.success();
  }
}
