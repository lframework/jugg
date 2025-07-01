package com.lframework.starter.web.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.inner.entity.SysDataPermissionData;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.inner.mappers.system.SysDataPermissionDataMapper;
import com.lframework.starter.web.inner.service.system.SysDataPermissionDataService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SysDataPermissionDataServiceImpl extends
    BaseMpServiceImpl<SysDataPermissionDataMapper, SysDataPermissionData> implements
    SysDataPermissionDataService {

  @Override
  public List<SysDataPermissionData> getByBizId(String bizId) {
    Wrapper<SysDataPermissionData> queryWrapper = Wrappers.lambdaQuery(SysDataPermissionData.class)
        .eq(SysDataPermissionData::getBizId, bizId);

    return this.list(queryWrapper);
  }

  @Override
  public SysDataPermissionData getByBizId(String bizId, Integer bizType, Integer permissionType) {
    Wrapper<SysDataPermissionData> queryWrapper = Wrappers.lambdaQuery(SysDataPermissionData.class)
        .eq(SysDataPermissionData::getBizId, bizId).eq(SysDataPermissionData::getBizType, bizType)
        .eq(SysDataPermissionData::getPermissionType, permissionType);

    return this.getOne(queryWrapper);
  }
}
