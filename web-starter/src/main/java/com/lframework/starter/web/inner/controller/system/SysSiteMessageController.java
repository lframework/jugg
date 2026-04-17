package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.components.threads.DefaultRunnable;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.bo.system.message.site.GetSysSiteMessageBo;
import com.lframework.starter.web.inner.bo.system.message.site.QueryMySysSiteMessageBo;
import com.lframework.starter.web.inner.bo.system.message.site.QuerySysSiteMessageBo;
import com.lframework.starter.web.inner.dto.message.site.SiteMessageDto;
import com.lframework.starter.web.inner.entity.SysSiteMessage;
import com.lframework.starter.web.inner.service.system.SysSiteMessageService;
import com.lframework.starter.web.inner.vo.system.message.site.QuerySysSiteMessageByUserVo;
import com.lframework.starter.web.inner.vo.system.message.site.QuerySysSiteMessageVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 站内信 Controller
 *
 * @author zmj
 */
@Tag(name = "站内信")
@Validated
@RestController
@RequestMapping("/system/message/site")
public class SysSiteMessageController extends DefaultBaseController {

  @Autowired
  private SysSiteMessageService sysSiteMessageService;

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
  @HasPermission("system:site-message:manage")
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysSiteMessageBo>> query(@Valid QuerySysSiteMessageVo vo) {

    PageResult<SysSiteMessage> pageResult = sysSiteMessageService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);

    List<SysSiteMessage> datas = pageResult.getDatas();
    List<QuerySysSiteMessageBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysSiteMessageBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询我的站内信
   */
  @Operation(summary = "查询我的站内信")
  @GetMapping("/query/my")
  public InvokeResult<PageResult<QueryMySysSiteMessageBo>> queryMySiteMessage(
      @Valid QuerySysSiteMessageByUserVo vo) {

    vo.setUserId(SecurityUtil.getCurrentUser().getId());

    PageResult<SysSiteMessage> pageResult = sysSiteMessageService.queryByUser(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysSiteMessage> datas = pageResult.getDatas();
    List<QueryMySysSiteMessageBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryMySysSiteMessageBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询内容
   */
  @Operation(summary = "根据ID查询内容")
  @Parameter(name = "id", description = "id", required = true)
  @GetMapping("/content")
  public InvokeResult<SiteMessageDto> getContent(@NotBlank(message = "id不能为空！") String id) {

    SiteMessageDto data = sysSiteMessageService.getContent(id);
    if (data == null) {
      throw new DefaultClientException("站内信不存在！");
    }

    String currentUserId = SecurityUtil.getCurrentUser().getId();
    ThreadUtil.execAsync(new DefaultRunnable(() -> {
      if (sysSiteMessageService.setReaded(id)) {
        sysSiteMessageService.noticeForWs(currentUserId);
      }
    }));

    return InvokeResultBuilder.success(data);
  }

  /**
   * 根据ID查询
   */
  @Operation(summary = "根据ID查询")
  @HasPermission("system:site-message:manage")
  @Parameter(name = "id", description = "id", required = true)
  @GetMapping
  public InvokeResult<GetSysSiteMessageBo> get(@NotBlank(message = "id不能为空！") String id) {

    SysSiteMessage data = sysSiteMessageService.findById(id);
    if (data == null) {
      throw new DefaultClientException("站内信不存在！");
    }

    GetSysSiteMessageBo result = new GetSysSiteMessageBo(data);

    return InvokeResultBuilder.success(result);
  }
}
