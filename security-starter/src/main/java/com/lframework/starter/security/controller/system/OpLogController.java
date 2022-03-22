package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.dto.DefaultOpLogsDto;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.IOpLogsService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.QueryOpLogsVo;
import com.lframework.starter.security.bo.system.oplog.GetOpLogBo;
import com.lframework.starter.security.bo.system.oplog.QueryOpLogBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/system/oplog")
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
public class OpLogController extends DefaultBaseController {

  @Autowired
  private IOpLogsService opLogsService;

  /**
   * 操作日志列表
   */
  @PreAuthorize("@permission.valid('system:oplog:query')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QueryOpLogsVo vo) {

    PageResult<DefaultOpLogsDto> pageResult = opLogsService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<DefaultOpLogsDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QueryOpLogBo> results = datas.stream().map(QueryOpLogBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 操作日志详情
   */
  @PreAuthorize("@permission.valid('system:oplog:query')")
  @GetMapping
  public InvokeResult getById(@NotBlank(message = "ID不能为空") String id) {

    DefaultOpLogsDto data = opLogsService.getById(id);

    if (data == null) {
      throw new DefaultClientException("操作日志不存在！");
    }

    GetOpLogBo result = new GetOpLogBo(data);

    return InvokeResultBuilder.success(result);
  }
}
