package com.lframework.starter.security.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.security.dto.system.config.SysConfigDto;
import com.lframework.starter.security.entity.SysConfig;
import com.lframework.starter.security.mappers.DefaultSysConfigMapper;
import com.lframework.starter.security.service.system.ISysConfigService;
import com.lframework.starter.security.vo.system.config.UpdateSysConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public class DefaultSysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private DefaultSysConfigMapper sysConfigMapper;

    @Cacheable(value = SysConfigDto.CACHE_NAME, key = "'config'", unless = "#result == null")
    @Override
    public SysConfigDto get() {

        return sysConfigMapper.get();
    }

    @Override
    public void update(UpdateSysConfigVo vo) {
        ISysConfigService thisService = getThis(this.getClass());

        Wrapper<SysConfig> updateWrapper = Wrappers.lambdaUpdate(SysConfig.class).set(SysConfig::getAllowRegist, vo.getAllowRegist()).set(SysConfig::getAllowLock, vo.getAllowLock()).set(SysConfig::getFailNum, vo.getFailNum()).set(SysConfig::getAllowCaptcha, vo.getAllowCaptcha());
        sysConfigMapper.update(updateWrapper);

        thisService.cleanCacheByKey("config");
    }

    @CacheEvict(value = SysConfigDto.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(String key) {

    }
}
