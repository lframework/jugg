package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.entity.SysParameter;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.ISysParameterService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.parameter.CreateSysParameterVo;
import com.lframework.starter.mybatis.vo.system.parameter.QuerySysParameterVo;
import com.lframework.starter.mybatis.vo.system.parameter.UpdateSysParameterVo;
import com.lframework.starter.security.bo.system.parameter.GetSysParameterBo;
import com.lframework.starter.security.bo.system.parameter.QuerySysParameterBo;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
  private ISysParameterService sysParameterService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @PreAuthorize("@permission.valid('system:parameter:query')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysParameterBo>> query(@Valid QuerySysParameterVo vo) {

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
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('system:parameter:query')")
  @GetMapping
  public InvokeResult<GetSysParameterBo> get(@NotNull(message = "id不能为空！") Long id) {

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
  @PreAuthorize("@permission.valid('system:parameter:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysParameterVo vo) {

    sysParameterService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @PreAuthorize("@permission.valid('system:parameter:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysParameterVo vo) {

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
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('system:parameter:delete')")
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotNull(message = "id不能为空！") Long id) {

    SysParameter data = sysParameterService.findById(id);

    sysParameterService.deleteById(id);

    sysParameterService.cleanCacheByKey(id);

    sysParameterService.cleanCacheByKey(data.getPmKey());

    return InvokeResultBuilder.success();
  }
}
