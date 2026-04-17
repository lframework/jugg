package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.bo.system.notify.GetSysNotifyGroupBo;
import com.lframework.starter.web.inner.bo.system.notify.QuerySysNotifyGroupBo;
import com.lframework.starter.web.inner.entity.SysNotifyGroup;
import com.lframework.starter.web.inner.service.system.SysNotifyGroupService;
import com.lframework.starter.web.inner.vo.system.notify.CreateSysNotifyGroupVo;
import com.lframework.starter.web.inner.vo.system.notify.QuerySysNotifyGroupVo;
import com.lframework.starter.web.inner.vo.system.notify.UpdateSysNotifyGroupVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息通知组 Controller
 *
 * @author zmj
 */
@Tag(name = "消息通知组")
@Validated
@RestController
@RequestMapping("/sys/notify/group")
public class SysNotifyGroupController extends DefaultBaseController {

  @Autowired
  private SysNotifyGroupService sysNotifyGroupService;

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
  @HasPermission({"system:notify-group:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysNotifyGroupBo>> query(
      @Valid QuerySysNotifyGroupVo vo) {

    PageResult<SysNotifyGroup> pageResult = sysNotifyGroupService.query(
        getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysNotifyGroup> datas = pageResult.getDatas();
    List<QuerySysNotifyGroupBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysNotifyGroupBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @Operation(summary = "根据ID查询")
  @Parameter(name = "id", description = "ID", required = true)
  @HasPermission({"system:notify-group:query"})
  @GetMapping("/detail")
  public InvokeResult<GetSysNotifyGroupBo> getDetail(
      @NotBlank(message = "id不能为空！") String id) {

    SysNotifyGroup data = sysNotifyGroupService.findById(id);
    if (data == null) {
      throw new DefaultClientException("消息通知组不存在！");
    }

    GetSysNotifyGroupBo result = new GetSysNotifyGroupBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @Operation(summary = "新增")
  @HasPermission({"system:notify-group:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateSysNotifyGroupVo vo) {

    sysNotifyGroupService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @Operation(summary = "修改")
  @HasPermission({"system:notify-group:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateSysNotifyGroupVo vo) {

    sysNotifyGroupService.update(vo);

    sysNotifyGroupService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "根据ID删除")
  @HasPermission({"system:notify-group:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "ID不能为空！") String id) {

    sysNotifyGroupService.deleteById(id);
    sysNotifyGroupService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
