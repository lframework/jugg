package com.lframework.starter.security.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.entity.DefaultOpLogs;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.OpLogsService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.QueryOpLogsVo;
import com.lframework.starter.security.bo.system.oplog.GetOpLogBo;
import com.lframework.starter.security.bo.system.oplog.QueryOpLogBo;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import com.lframework.starter.web.annotations.security.HasPermission;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志
 *
 * @author zmj
 */
@Api(tags = "操作日志")
@Validated
@RestController
@RequestMapping("/system/oplog")
public class OpLogController extends DefaultBaseController {

  @Autowired
  private OpLogsService opLogsService;

  /**
   * 操作日志列表
   */
  @ApiOperation("操作日志列表")
  @HasPermission({"system:oplog:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryOpLogBo>> query(@Valid QueryOpLogsVo vo) {

    PageResult<DefaultOpLogs> pageResult = opLogsService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<DefaultOpLogs> datas = pageResult.getDatas();
    List<QueryOpLogBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryOpLogBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 操作日志详情
   */
  @ApiOperation("操作日志详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:oplog:query"})
  @GetMapping
  public InvokeResult<GetOpLogBo> findById(@NotBlank(message = "ID不能为空") String id) {

    DefaultOpLogs data = opLogsService.findById(id);

    if (data == null) {
      throw new DefaultClientException("操作日志不存在！");
    }

    GetOpLogBo result = new GetOpLogBo(data);

    return InvokeResultBuilder.success(result);
  }
}
