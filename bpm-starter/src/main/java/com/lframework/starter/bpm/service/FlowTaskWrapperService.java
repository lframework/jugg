package com.lframework.starter.bpm.service;

import com.lframework.starter.bpm.dto.FlowTaskDto;
import com.lframework.starter.bpm.entity.FlowTaskWrapper;
import com.lframework.starter.bpm.vo.flow.instance.QueryInstanceListVo;
import com.lframework.starter.bpm.vo.flow.task.ApprovePassTaskVo;
import com.lframework.starter.bpm.vo.flow.task.ApproveRefuseTaskVo;
import com.lframework.starter.bpm.vo.flow.task.QueryMyTaskListVo;
import com.lframework.starter.bpm.vo.flow.task.QueryTodoTaskListVo;
import com.lframework.starter.bpm.vo.flow.task.RejectTaskVo;
import com.lframework.starter.bpm.vo.flow.task.UndoTaskVo;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;

public interface FlowTaskWrapperService extends BaseMpService<FlowTaskWrapper> {

  /**
   * 查询待办任务列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<FlowTaskDto> queryTodoList(Integer pageIndex, Integer pageSize,
      QueryTodoTaskListVo vo);

  /**
   * 查询我的发起列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<FlowTaskDto> queryMyList(Integer pageIndex, Integer pageSize,
      QueryMyTaskListVo vo);

  /**
   * 查询流程实例列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<FlowTaskDto> queryInstanceList(Integer pageIndex, Integer pageSize,
      QueryInstanceListVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassTaskVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseTaskVo vo);

  /**
   * 撤回
   *
   * @param vo
   */
  void undo(UndoTaskVo vo);

  /**
   * 反对
   *
   * @param vo
   */
  void reject(RejectTaskVo vo);

  /**
   * 终止
   *
   * @param instanceId
   */
  void termination(Long instanceId);
}
