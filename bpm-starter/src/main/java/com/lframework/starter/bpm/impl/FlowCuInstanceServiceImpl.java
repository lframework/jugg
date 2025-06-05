package com.lframework.starter.bpm.impl;

import com.lframework.starter.bpm.entity.FlowCuInstance;
import com.lframework.starter.bpm.mappers.FlowCuInstanceMapper;
import com.lframework.starter.bpm.service.FlowCuInstanceService;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FlowCuInstanceServiceImpl extends
    BaseMpServiceImpl<FlowCuInstanceMapper, FlowCuInstance> implements
    FlowCuInstanceService {

}
