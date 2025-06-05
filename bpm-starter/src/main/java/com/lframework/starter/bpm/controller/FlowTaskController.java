package com.lframework.starter.bpm.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.bpm.bo.flow.task.QueryInstanceListBo;
import com.lframework.starter.bpm.bo.flow.task.QueryMyTaskListBo;
import com.lframework.starter.bpm.bo.flow.task.QueryTodoTaskListBo;
import com.lframework.starter.bpm.dto.FlowTaskDto;
import com.lframework.starter.bpm.enums.FlowCooperateType;
import com.lframework.starter.bpm.service.FlowTaskWrapperService;
import com.lframework.starter.bpm.vo.flow.instance.QueryInstanceListVo;
import com.lframework.starter.bpm.vo.flow.task.ApprovePassTaskVo;
import com.lframework.starter.bpm.vo.flow.task.ApproveRefuseTaskVo;
import com.lframework.starter.bpm.vo.flow.task.QueryMyTaskListVo;
import com.lframework.starter.bpm.vo.flow.task.QueryTodoTaskListVo;
import com.lframework.starter.bpm.vo.flow.task.RejectTaskVo;
import com.lframework.starter.bpm.vo.flow.task.UndoTaskVo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.PageResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.dromara.warm.flow.core.entity.Task;
import org.dromara.warm.flow.core.enums.CooperateType;
import org.dromara.warm.flow.core.service.TaskService;
import org.dromara.warm.flow.orm.entity.FlowNode;
import org.dromara.warm.flow.orm.mapper.FlowNodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "流程任务")
@Validated
@RestController
@RequestMapping("/flow/task")
public class FlowTaskController extends DefaultBaseController {

  @Autowired
  private FlowTaskWrapperService flowTaskWrapperService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private FlowNodeMapper flowNodeMapper;

  @ApiOperation("查询待办任务列表")
  @GetMapping("/list/todo")
  public InvokeResult<PageResult<QueryTodoTaskListBo>> queryTodoList(
      @Valid QueryTodoTaskListVo vo) {
    PageResult<FlowTaskDto> pageResult = flowTaskWrapperService.queryTodoList(getPageIndex(vo),
        getPageSize(vo), vo);
    List<FlowTaskDto> datas = pageResult.getDatas();
    List<QueryTodoTaskListBo> results = datas.stream().map(QueryTodoTaskListBo::new).collect(
        Collectors.toList());

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("查询我的发起列表")
  @GetMapping("/list/my")
  public InvokeResult<PageResult<QueryMyTaskListBo>> queryMyList(
      @Valid QueryMyTaskListVo vo) {
    PageResult<FlowTaskDto> pageResult = flowTaskWrapperService.queryMyList(getPageIndex(vo),
        getPageSize(vo), vo);
    List<FlowTaskDto> datas = pageResult.getDatas();
    List<QueryMyTaskListBo> results = datas.stream().map(QueryMyTaskListBo::new).collect(
        Collectors.toList());

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("查询流程实例列表")
  @GetMapping("/list/instance")
  public InvokeResult<PageResult<QueryInstanceListBo>> queryInstanceList(
      @Valid QueryInstanceListVo vo) {
    PageResult<FlowTaskDto> pageResult = flowTaskWrapperService.queryInstanceList(getPageIndex(vo),
        getPageSize(vo), vo);
    List<FlowTaskDto> datas = pageResult.getDatas();
    List<QueryInstanceListBo> results = datas.stream().map(QueryInstanceListBo::new).collect(
        Collectors.toList());

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("根据ID查询审核类型")
  @ApiImplicitParam(value = "流程任务ID", name = "id", paramType = "query", required = true)
  @GetMapping("/cooperate")
  public InvokeResult<Integer> getCooperateType(@NotNull(message = "流程任务ID不能为空！") Long id) {
    Task task = taskService.getById(id);
    if (task == null) {
      throw new DefaultClientException("流程任务不存在！");
    }
    Long definitionId = task.getDefinitionId();
    String nodeCode = task.getNodeCode();
    Wrapper<FlowNode> queryWrapper = Wrappers.lambdaQuery(FlowNode.class)
        .eq(FlowNode::getDefinitionId, definitionId)
        .eq(FlowNode::getNodeCode, nodeCode);
    FlowNode flowNode = flowNodeMapper.selectOne(queryWrapper);
    if (flowNode == null) {
      throw new DefaultClientException("流程节点不存在！");
    }

    if (CooperateType.isCountersign(flowNode.getNodeRatio())) {
      return InvokeResultBuilder.success(FlowCooperateType.ALL.getCode());
    } else if (CooperateType.isVoteSign(flowNode.getNodeRatio())) {
      return InvokeResultBuilder.success(FlowCooperateType.VOTE.getCode());
    } else {
      return InvokeResultBuilder.success(FlowCooperateType.NORMAL.getCode());
    }
  }

  @ApiOperation("审核通过")
  @PostMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@Valid @RequestBody ApprovePassTaskVo vo) {
    flowTaskWrapperService.approvePass(vo);
    return InvokeResultBuilder.success();
  }

  @ApiOperation("审核拒绝")
  @PostMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@Valid @RequestBody ApproveRefuseTaskVo vo) {
    flowTaskWrapperService.approveRefuse(vo);
    return InvokeResultBuilder.success();
  }

  @ApiOperation("撤回")
  @PostMapping("/undo")
  public InvokeResult<Void> undo(@Valid @RequestBody UndoTaskVo vo) {
    flowTaskWrapperService.undo(vo);
    return InvokeResultBuilder.success();
  }

  @ApiOperation("反对")
  @PostMapping("/reject")
  public InvokeResult<Void> reject(@Valid @RequestBody RejectTaskVo vo) {
    flowTaskWrapperService.reject(vo);
    return InvokeResultBuilder.success();
  }

  @ApiOperation("终止")
  @PostMapping("/termination")
  public InvokeResult<Void> termination(@NotNull(message = "流程实例ID不能为空！") Long instanceId) {
    flowTaskWrapperService.termination(instanceId);
    return InvokeResultBuilder.success();
  }
}
