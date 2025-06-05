package com.lframework.starter.bpm.mappers;

import com.lframework.starter.bpm.dto.FlowTaskDto;
import com.lframework.starter.bpm.entity.FlowTaskWrapper;
import com.lframework.starter.bpm.vo.flow.instance.QueryInstanceListVo;
import com.lframework.starter.bpm.vo.flow.task.QueryMyTaskListVo;
import com.lframework.starter.bpm.vo.flow.task.QueryTodoTaskListVo;
import com.lframework.starter.web.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlowTaskWrapperMapper extends BaseMapper<FlowTaskWrapper> {

  /**
   * 查询待办任务列表
   *
   * @param vo
   * @return
   */
  List<FlowTaskDto> queryTodoList(@Param("vo") QueryTodoTaskListVo vo,
      @Param("userId") String userId);

  /**
   * 查询我的发起列表
   *
   * @param vo
   * @return
   */
  List<FlowTaskDto> queryMyList(@Param("vo") QueryMyTaskListVo vo, @Param("userId") String userId);

  /**
   * 查询流程实例列表
   *
   * @param vo
   * @return
   */
  List<FlowTaskDto> queryInstanceList(@Param("vo") QueryInstanceListVo vo);
}
