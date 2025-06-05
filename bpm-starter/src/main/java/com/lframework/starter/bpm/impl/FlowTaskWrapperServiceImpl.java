package com.lframework.starter.bpm.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.bpm.dto.FlowTaskDto;
import com.lframework.starter.bpm.entity.FlowTaskWrapper;
import com.lframework.starter.bpm.enums.FlowInstanceStatus;
import com.lframework.starter.bpm.mappers.FlowTaskWrapperMapper;
import com.lframework.starter.bpm.service.FlowTaskWrapperService;
import com.lframework.starter.bpm.vo.flow.instance.QueryInstanceListVo;
import com.lframework.starter.bpm.vo.flow.task.ApprovePassTaskVo;
import com.lframework.starter.bpm.vo.flow.task.ApproveRefuseTaskVo;
import com.lframework.starter.bpm.vo.flow.task.QueryMyTaskListVo;
import com.lframework.starter.bpm.vo.flow.task.QueryTodoTaskListVo;
import com.lframework.starter.bpm.vo.flow.task.RejectTaskVo;
import com.lframework.starter.bpm.vo.flow.task.UndoTaskVo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.constant.ExceptionCons;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.HisTask;
import org.dromara.warm.flow.core.entity.Task;
import org.dromara.warm.flow.core.entity.User;
import org.dromara.warm.flow.core.enums.CooperateType;
import org.dromara.warm.flow.core.enums.SkipType;
import org.dromara.warm.flow.core.enums.UserType;
import org.dromara.warm.flow.core.service.TaskService;
import org.dromara.warm.flow.core.utils.CollUtil;
import org.dromara.warm.flow.core.utils.StreamUtils;
import org.dromara.warm.flow.orm.entity.FlowNode;
import org.dromara.warm.flow.orm.mapper.FlowNodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowTaskWrapperServiceImpl extends
    BaseMpServiceImpl<FlowTaskWrapperMapper, FlowTaskWrapper> implements
    FlowTaskWrapperService {

  @Autowired
  private TaskService taskService;

  @Autowired
  private FlowNodeMapper flowNodeMapper;

  @Override
  public PageResult<FlowTaskDto> queryTodoList(Integer pageIndex, Integer pageSize,
      QueryTodoTaskListVo vo) {

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<FlowTaskDto> datas = getBaseMapper().queryTodoList(vo,
        SecurityUtil.getCurrentUser().getId());
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public PageResult<FlowTaskDto> queryMyList(Integer pageIndex, Integer pageSize,
      QueryMyTaskListVo vo) {
    PageHelperUtil.startPage(pageIndex, pageSize);
    List<FlowTaskDto> datas = getBaseMapper().queryMyList(vo,
        SecurityUtil.getCurrentUser().getId());
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public PageResult<FlowTaskDto> queryInstanceList(Integer pageIndex, Integer pageSize,
      QueryInstanceListVo vo) {
    PageHelperUtil.startPage(pageIndex, pageSize);
    List<FlowTaskDto> datas = getBaseMapper().queryInstanceList(vo);
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassTaskVo vo) {
    taskService.pass(vo.getTaskId(), vo.getMessage(), vo.getVariables());
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefuseTaskVo vo) {
    FlowParams flowParams = new FlowParams();
    flowParams.message(vo.getMessage());
    flowParams.skipType("REJECT");
    flowParams.flowStatus(FlowInstanceStatus.REFUSE.getCode());
    flowParams.variable(vo.getVariables());
    taskService.termination(vo.getTaskId(), flowParams);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void undo(UndoTaskVo vo) {
    FlowParams flowParams = new FlowParams();
    flowParams.message(vo.getMessage());
    flowParams.skipType("REJECT");
    flowParams.flowStatus(FlowInstanceStatus.UNDO.getCode());
    flowParams.variable(vo.getVariables());
    flowParams.ignore(true);
    taskService.terminationByInsId(vo.getInstanceId(), flowParams);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void reject(RejectTaskVo vo) {

    Task task = taskService.getById(vo.getTaskId());
    Long definitionId = task.getDefinitionId();
    String nodeCode = task.getNodeCode();
    Wrapper<FlowNode> queryWrapper = Wrappers.lambdaQuery(FlowNode.class)
        .eq(FlowNode::getDefinitionId, definitionId)
        .eq(FlowNode::getNodeCode, nodeCode);
    FlowNode flowNode = flowNodeMapper.selectOne(queryWrapper);
    if (flowNode == null) {
      throw new DefaultClientException("流程节点不存在！");
    }

    BigDecimal nodeRatio = flowNode.getNodeRatio();

    // 办理人和转办人列表
    List<User> todoList = FlowEngine.userService().listByAssociatedAndTypes(vo.getTaskId()
        , UserType.APPROVAL.getKey(), UserType.TRANSFER.getKey(), UserType.DEPUTE.getKey());

    String currentUserId = SecurityUtil.getCurrentUser().getId();
    // 判断办理人是否有办理权限
    User todoUser = CollUtil.getOne(
        StreamUtils.filter(todoList, u -> Objects.equals(u.getProcessedBy(), currentUserId)));
    if (ObjectUtil.isNull(todoUser)) {
      throw new DefaultClientException(ExceptionCons.NOT_AUTHORITY);
    }

    FlowParams terminationParams = new FlowParams();
    terminationParams.message(vo.getMessage());
    terminationParams.skipType("REJECT");
    terminationParams.flowStatus(FlowInstanceStatus.REVOKE.getCode());
    terminationParams.variable(vo.getVariables());

    // 当只剩一位待办用户时，由当前用户决定走向
    if (todoList.size() == 1) {
      taskService.termination(vo.getTaskId(), terminationParams);
      return;
    }

    // 查询会签票签已办列表
    List<HisTask> doneList = FlowEngine.hisTaskService().listByTaskIdAndCooperateTypes(task.getId()
        , CooperateType.isCountersign(nodeRatio) ? CooperateType.COUNTERSIGN.getKey()
            : CooperateType.VOTE.getKey());
    doneList = CollUtil.emptyDefault(doneList);

    // 所有人
    BigDecimal all = BigDecimal.ZERO.add(BigDecimal.valueOf(todoList.size()))
        .add(BigDecimal.valueOf(doneList.size()));

    List<HisTask> doneRejectList = StreamUtils.filter(doneList
        , hisTask -> Objects.equals(hisTask.getSkipType(), SkipType.REJECT.getKey()));

    // 计算驳回率
    BigDecimal rejectRatio = (BigDecimal.ONE)
        .add(BigDecimal.valueOf(doneRejectList.size()))
        .divide(all, 4, RoundingMode.HALF_UP).multiply(CooperateType.ONE_HUNDRED);

    if (rejectRatio.compareTo(CooperateType.ONE_HUNDRED.subtract(nodeRatio)) > 0) {
      taskService.termination(vo.getTaskId(), terminationParams);
      return;
    }

    taskService.reject(vo.getTaskId(), vo.getMessage(), vo.getVariables());
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void termination(Long instanceId) {
    FlowParams flowParams = new FlowParams();
    flowParams.skipType("REJECT");
    flowParams.flowStatus(FlowInstanceStatus.TERMINATION.getCode());
    flowParams.ignore(true);
    taskService.terminationByInsId(instanceId, flowParams);
  }
}
