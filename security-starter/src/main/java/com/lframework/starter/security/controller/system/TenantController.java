package com.lframework.starter.security.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.mybatis.entity.Tenant;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.TenantService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.tenant.CreateTenantVo;
import com.lframework.starter.mybatis.vo.system.tenant.QueryTenantVo;
import com.lframework.starter.mybatis.vo.system.tenant.UpdateTenantVo;
import com.lframework.starter.security.bo.system.tenant.GetTenantBo;
import com.lframework.starter.security.bo.system.tenant.QueryTenantBo;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户管理
 *
 * @author zmj
 */
@Api(tags = "租户管理")
@Validated
@RestController
@RequestMapping("/system/tenant")
public class TenantController extends DefaultBaseController {

  @Autowired
  private TenantService tenantService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"system:tenant:query", "system:tenant:add", "system:tenant:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryTenantBo>> query(@Valid QueryTenantVo vo) {

    PageResult<Tenant> pageResult = tenantService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<Tenant> datas = pageResult.getDatas();
    List<QueryTenantBo> results = datas.stream().map(QueryTenantBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询详情
   */
  @ApiOperation("查询详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:tenant:query", "system:tenant:add", "system:tenant:modify"})
  @GetMapping
  public InvokeResult<GetTenantBo> get(@NotNull(message = "ID不能为空！") Integer id) {

    Tenant data = tenantService.findById(id);
    if (data == null) {
      throw new DefaultClientException("租户不存在！");
    }

    GetTenantBo result = new GetTenantBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增租户
   */
  @ApiOperation("新增租户")
  @HasPermission({"system:tenant:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateTenantVo vo) {

    tenantService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改租户
   */
  @ApiOperation("修改租户")
  @HasPermission({"system:tenant:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateTenantVo vo) {

    tenantService.update(vo);

    tenantService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
