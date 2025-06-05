package com.lframework.starter.bpm.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.bpm.entity.FlowInstanceWrapper;
import com.lframework.starter.bpm.enums.FlowInstanceStatus;
import com.lframework.starter.bpm.mappers.FlowInstanceWrapperMapper;
import com.lframework.starter.bpm.service.FlowInstanceWrapperService;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FlowInstanceWrapperServiceImpl extends
    BaseMpServiceImpl<FlowInstanceWrapperMapper, FlowInstanceWrapper> implements
    FlowInstanceWrapperService {

  @Override
  public Boolean canRestart(String businessId) {
    Wrapper<FlowInstanceWrapper> queryWrapper = Wrappers.lambdaQuery(FlowInstanceWrapper.class)
        .eq(FlowInstanceWrapper::getBusinessId, businessId)
        .in(FlowInstanceWrapper::getFlowStatus, FlowInstanceStatus.CREATE,
            FlowInstanceStatus.APPROVING).last("LIMIT 1");

    return this.getOne(queryWrapper) == null;
  }
}
