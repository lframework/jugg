package com.lframework.starter.bpm.service;

import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.vo.flow.category.CreateFlowCategoryVo;
import com.lframework.starter.bpm.vo.flow.category.UpdateFlowCategoryVo;
import com.lframework.starter.web.core.service.BaseMpService;

public interface FlowCategoryService extends BaseMpService<FlowCategory> {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  FlowCategory findById(String id);

  /**
   * 新增
   *
   * @param vo
   * @return
   */
  String create(CreateFlowCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   * @return
   */
  void update(UpdateFlowCategoryVo vo);
}
