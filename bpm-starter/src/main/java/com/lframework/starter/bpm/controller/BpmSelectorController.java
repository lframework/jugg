package com.lframework.starter.bpm.controller;

import com.lframework.starter.bpm.bo.flow.definition.FlowDefinitionSelectorBo;
import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.service.FlowDefinitionWrapperService;
import com.lframework.starter.bpm.vo.flow.definition.FlowDefinitionSelectorVo;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BPM选择器
 *
 * @author zmj
 */
@Tag(name = "BPM选择器")
@Validated
@RestController
@RequestMapping("/selector/bpm")
public class BpmSelectorController extends DefaultBaseController {

  @Autowired
  private FlowDefinitionWrapperService flowDefinitionWrapperService;

  /**
   * 流程定义
   */
  @Operation(summary = "流程定义")
  @GetMapping("/flow/definition")
  public InvokeResult<PageResult<FlowDefinitionSelectorBo>> selector(
      @Valid FlowDefinitionSelectorVo vo) {

    PageResult<FlowDefinitionWrapper> pageResult = flowDefinitionWrapperService.selector(
        getPageIndex(vo), getPageSize(vo), vo);
    List<FlowDefinitionWrapper> datas = pageResult.getDatas();
    List<FlowDefinitionSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(FlowDefinitionSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载流程定义
   */
  @Operation(summary = "加载流程定义")
  @PostMapping("/flow/definition/load")
  public InvokeResult<List<FlowDefinitionSelectorBo>> loadFlowDefinition(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<FlowDefinitionWrapper> datas = flowDefinitionWrapperService.listByIds(ids);
    List<FlowDefinitionSelectorBo> results = datas.stream().map(FlowDefinitionSelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }
}
