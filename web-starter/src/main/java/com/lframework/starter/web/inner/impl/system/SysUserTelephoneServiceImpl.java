package com.lframework.starter.web.inner.impl.system;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.inner.mappers.system.SysUserTelephoneMapper;
import com.lframework.starter.web.inner.entity.SysUserTelephone;
import com.lframework.starter.web.inner.service.system.SysUserTelephoneService;
import org.springframework.stereotype.Service;

@Service
public class SysUserTelephoneServiceImpl extends
    BaseMpServiceImpl<SysUserTelephoneMapper, SysUserTelephone> implements
    SysUserTelephoneService {

}
