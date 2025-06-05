package com.lframework.starter.bpm.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.mappers.FlowDefinitionWrapperMapper;
import com.lframework.starter.bpm.service.FlowDefinitionWrapperService;
import com.lframework.starter.bpm.vo.flow.definition.FlowDefinitionSelectorVo;
import com.lframework.starter.bpm.vo.flow.definition.UpdateFlowDefinitionVo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import java.util.List;
import org.dromara.warm.flow.core.entity.Definition;
import org.dromara.warm.flow.core.service.DefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowDefinitionWrapperServiceImpl extends
    BaseMpServiceImpl<FlowDefinitionWrapperMapper, FlowDefinitionWrapper> implements
    FlowDefinitionWrapperService {

  @Autowired
  private DefService defService;

  @Override
  public PageResult<FlowDefinitionWrapper> selector(Integer pageIndex, Integer pageSize,
      FlowDefinitionSelectorVo vo) {

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<FlowDefinitionWrapper> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateFlowDefinitionVo vo) {
    Wrapper<FlowDefinitionWrapper> updateWrapper = Wrappers.lambdaUpdate(
            FlowDefinitionWrapper.class)
        .eq(FlowDefinitionWrapper::getId, vo.getId())
        .set(FlowDefinitionWrapper::getFlowName, vo.getName())
        .set(FlowDefinitionWrapper::getCategory, vo.getCategoryId());

    this.update(updateWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void copy(UpdateFlowDefinitionVo vo) {
    Definition definition = defService.getById(vo.getId());
    if (definition == null) {
      throw new DefaultClientException("流程不存在！");
    }

    defService.copyDef(vo.getId());

    Wrapper<FlowDefinitionWrapper> queryWrapper = Wrappers.lambdaQuery(FlowDefinitionWrapper.class)
        .eq(FlowDefinitionWrapper::getFlowCode, definition.getFlowCode())
        .orderByDesc(FlowDefinitionWrapper::getCreateTime)
        .last("LIMIT 1");
    FlowDefinitionWrapper flowDefinitionWrapper = this.getOne(queryWrapper);

    vo.setId(flowDefinitionWrapper.getId());
    this.update(vo);

    defService.unActive(flowDefinitionWrapper.getId());
  }
}
