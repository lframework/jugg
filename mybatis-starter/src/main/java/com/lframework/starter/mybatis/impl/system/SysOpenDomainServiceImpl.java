package com.lframework.starter.mybatis.impl.system;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.SysOpenDomain;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.SysOpenDomainMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.SysOpenDomainService;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.open.CreateSysOpenDomainVo;
import com.lframework.starter.mybatis.vo.system.open.QuerySysOpenDomainVo;
import com.lframework.starter.mybatis.vo.system.open.SysOpenDomainSelectorVo;
import com.lframework.starter.mybatis.vo.system.open.UpdateSysOpenDomainSecretVo;
import com.lframework.starter.mybatis.vo.system.open.UpdateSysOpenDomainVo;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.utils.TenantUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DS("master")
@Service
public class SysOpenDomainServiceImpl extends
    BaseMpServiceImpl<SysOpenDomainMapper, SysOpenDomain>
    implements SysOpenDomainService {

  @Override
  public PageResult<SysOpenDomain> query(QuerySysOpenDomainVo vo) {
    PageHelperUtil.startPage(vo);
    List<SysOpenDomain> datas = getBaseMapper().query(vo);
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public PageResult<SysOpenDomain> selector(SysOpenDomainSelectorVo vo) {
    PageHelperUtil.startPage(vo);
    List<SysOpenDomain> datas = getBaseMapper().selector(vo);
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = SysOpenDomain.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public SysOpenDomain findById(Integer id) {
    return this.getById(id);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增开放域，ID：{}", params = {
      "#_result"}, autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysOpenDomainVo vo) {
    SysOpenDomain data = new SysOpenDomain();
    data.setName(vo.getName());
    data.setApiSecret(vo.getApiSecret());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    if (vo.getTenantId() != null) {
      data.setTenantId(vo.getTenantId());
    }

    this.save(data);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改开放域，ID：{}", params = {
      "#vo.id"}, autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysOpenDomainVo vo) {

    SysOpenDomain record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("开放域不存在！");
    }

    LambdaUpdateWrapper<SysOpenDomain> updateWrapper = Wrappers.lambdaUpdate(SysOpenDomain.class)
        .set(SysOpenDomain::getName, vo.getName())
        .set(SysOpenDomain::getAvailable, vo.getAvailable())
        .set(SysOpenDomain::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(SysOpenDomain::getTenantId, vo.getTenantId() == null ? null : vo.getTenantId())
        .eq(SysOpenDomain::getId, vo.getId());

    this.update(updateWrapper);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改开放域Api密钥，ID：{}", params = {
      "#vo.id"}, autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateApiSecret(UpdateSysOpenDomainSecretVo vo) {
    SysOpenDomain record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("开放域不存在！");
    }

    LambdaUpdateWrapper<SysOpenDomain> updateWrapper = Wrappers.lambdaUpdate(SysOpenDomain.class)
        .set(SysOpenDomain::getApiSecret, vo.getApiSecret())
        .eq(SysOpenDomain::getId, vo.getId());

    this.update(updateWrapper);
  }

  @CacheEvict(value = SysOpenDomain.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
