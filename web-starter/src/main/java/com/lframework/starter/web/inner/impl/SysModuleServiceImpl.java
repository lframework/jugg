package com.lframework.starter.web.inner.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.inner.entity.SysModule;
import com.lframework.starter.web.inner.mappers.SysModuleMapper;
import com.lframework.starter.web.inner.service.SysModuleService;
import org.springframework.stereotype.Service;

@DS("master")
@Service
public class SysModuleServiceImpl extends BaseMpServiceImpl<SysModuleMapper, SysModule> implements
    SysModuleService {

}
