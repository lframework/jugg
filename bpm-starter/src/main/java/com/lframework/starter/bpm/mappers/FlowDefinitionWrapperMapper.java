package com.lframework.starter.bpm.mappers;

import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.vo.flow.definition.FlowDefinitionSelectorVo;
import com.lframework.starter.web.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlowDefinitionWrapperMapper extends BaseMapper<FlowDefinitionWrapper> {

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<FlowDefinitionWrapper> selector(@Param("vo") FlowDefinitionSelectorVo vo);
}
