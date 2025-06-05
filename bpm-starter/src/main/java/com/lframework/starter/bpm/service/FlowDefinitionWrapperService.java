package com.lframework.starter.bpm.service;

import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.vo.flow.definition.FlowDefinitionSelectorVo;
import com.lframework.starter.bpm.vo.flow.definition.UpdateFlowDefinitionVo;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;

public interface FlowDefinitionWrapperService extends BaseMpService<FlowDefinitionWrapper> {

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<FlowDefinitionWrapper> selector(Integer pageIndex, Integer pageSize,
      FlowDefinitionSelectorVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateFlowDefinitionVo vo);

  /**
   * 复制
   *
   * @param vo
   */
  void copy(UpdateFlowDefinitionVo vo);
}
