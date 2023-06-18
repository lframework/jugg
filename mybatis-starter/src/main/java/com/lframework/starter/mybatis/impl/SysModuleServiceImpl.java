package com.lframework.starter.mybatis.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.lframework.starter.mybatis.entity.SysModule;
import com.lframework.starter.mybatis.mappers.SysModuleMapper;
import com.lframework.starter.mybatis.service.SysModuleService;
import org.springframework.stereotype.Service;

@DS("master")
@Service
public class SysModuleServiceImpl extends BaseMpServiceImpl<SysModuleMapper, SysModule> implements
    SysModuleService {

}
