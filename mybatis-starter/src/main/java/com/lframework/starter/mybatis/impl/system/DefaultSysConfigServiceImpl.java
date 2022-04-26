package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.SysConfig;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.DefaultSysConfigMapper;
import com.lframework.starter.mybatis.service.system.ISysConfigService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.system.config.UpdateSysConfigVo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public class DefaultSysConfigServiceImpl extends
    BaseMpServiceImpl<DefaultSysConfigMapper, SysConfig>
    implements ISysConfigService {

  @Cacheable(value = SysConfig.CACHE_NAME, key = "'config'", unless = "#result == null")
  @Override
  public SysConfig get() {

    return getBaseMapper().selectOne(Wrappers.lambdaQuery(SysConfig.class));
  }

  @OpLog(type = OpLogType.OTHER, name = "修改系统设置")
  @Override
  public void update(UpdateSysConfigVo vo) {

    ISysConfigService thisService = getThis(this.getClass());

    Wrapper<SysConfig> updateWrapper = Wrappers.lambdaUpdate(SysConfig.class)
        .set(SysConfig::getAllowRegist, vo.getAllowRegist())
        .set(SysConfig::getAllowLock, vo.getAllowLock())
        .set(SysConfig::getFailNum, vo.getFailNum())
        .set(SysConfig::getAllowCaptcha, vo.getAllowCaptcha())
        .set(SysConfig::getAllowForgetPsw, vo.getAllowForgetPsw())
        .set(SysConfig::getForgetPswRequireMail, vo.getForgetPswRequireMail())
        .set(SysConfig::getForgetPswRequireSms, vo.getForgetPswRequireSms())
        .set(SysConfig::getSignName, vo.getSignName())
        .set(SysConfig::getTemplateCode, vo.getTemplateCode())
        .set(SysConfig::getAllowTelephoneLogin, vo.getAllowTelephoneLogin())
        .set(SysConfig::getTelephoneLoginSignName, vo.getTelephoneLoginSignName())
        .set(SysConfig::getTelephoneLoginTemplateCode, vo.getTelephoneLoginTemplateCode());
    getBaseMapper().update(updateWrapper);

    OpLogUtil.setExtra(vo);

    thisService.cleanCacheByKey("config");
  }

  @CacheEvict(value = SysConfig.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}
