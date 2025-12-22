package com.lframework.starter.web.inner.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.DataSourceUtil;
import com.lframework.starter.web.core.utils.EncryptUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.inner.mappers.TenantMapper;
import com.lframework.starter.web.inner.service.TenantService;
import com.lframework.starter.web.inner.vo.system.tenant.CreateTenantVo;
import com.lframework.starter.web.inner.vo.system.tenant.QueryTenantVo;
import com.lframework.starter.web.inner.vo.system.tenant.TenantSelectorVo;
import com.lframework.starter.web.inner.vo.system.tenant.UpdateTenantVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DS("master")
@Service
public class TenantServiceImpl extends BaseMpServiceImpl<TenantMapper, Tenant> implements
    TenantService {

  @Override
  public PageResult<Tenant> query(Integer pageIndex, Integer pageSize, QueryTenantVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Tenant> datas = getBaseMapper().query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public PageResult<Tenant> selector(Integer pageIndex, Integer pageSize, TenantSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Tenant> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = Tenant.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public Tenant findById(Integer id) {
    return getById(id);
  }

  @Cacheable(value = Tenant.CACHE_NAME, key = "'all'", unless = "#result == null")
  @Override
  public List<Tenant> findAll() {
    Wrapper<Tenant> queryWrapper = Wrappers.lambdaQuery(Tenant.class)
        .eq(Tenant::getAvailable, Boolean.TRUE);

    return list(queryWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public Integer create(CreateTenantVo data) {

    Wrapper<Tenant> checkWrapper = Wrappers.lambdaQuery(Tenant.class)
        .eq(Tenant::getName, data.getName());
    if (count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    if (data.getIsPlatform()) {
      checkWrapper = Wrappers.lambdaQuery(Tenant.class)
          .eq(Tenant::getIsPlatform, Boolean.TRUE);
      if (count(checkWrapper) > 0) {
        throw new DefaultClientException("平台管理租户只能有一个！");
      }
    }

    if (!DataSourceUtil.validConnection(data.getJdbcUrl(), data.getJdbcUsername(),
        data.getJdbcPassword())) {
      throw new DefaultClientException("数据库连接失败，请检查数据库配置！");
    }

    Tenant record = new Tenant();
    record.setName(data.getName());
    if (StringUtil.isNotBlank(data.getServerName())) {
      record.setServerName(data.getServerName());
    }
    record.setJdbcUrl(data.getJdbcUrl());
    record.setJdbcUsername(data.getJdbcUsername());
    record.setJdbcPassword(EncryptUtil.encrypt(data.getJdbcPassword()));
    record.setAvailable(Boolean.TRUE);

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateTenantVo data) {

    Tenant record = this.getById(data.getId());
    if (record == null) {
      throw new DefaultClientException("租户不存在！");
    }

    Wrapper<Tenant> checkWrapper = Wrappers.lambdaQuery(Tenant.class)
        .eq(Tenant::getName, data.getName()).ne(Tenant::getId, data.getId());
    if (count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    if (data.getIsPlatform()) {
      checkWrapper = Wrappers.lambdaQuery(Tenant.class)
          .eq(Tenant::getIsPlatform, Boolean.TRUE).ne(Tenant::getId, data.getId());
      if (count(checkWrapper) > 0) {
        throw new DefaultClientException("平台管理租户只能有一个！");
      }
    }

    if (!data.getAvailable() && data.getIsPlatform()) {
      throw new DefaultClientException("平台管理租户不允许停用！");
    }

    LambdaUpdateWrapper<Tenant> updateWrapper = Wrappers.lambdaUpdate(Tenant.class)
        .eq(Tenant::getId, data.getId()).set(Tenant::getName, data.getName())
        .set(Tenant::getServerName, data.getServerName())
        .set(Tenant::getAvailable, data.getAvailable());

    boolean validConnection = false;
    if (StringUtil.isNotBlank(data.getJdbcUrl())) {
      updateWrapper.set(Tenant::getJdbcUrl, data.getJdbcUrl());

      validConnection = true;
    }
    if (StringUtil.isNotBlank(data.getJdbcUsername())) {
      updateWrapper.set(Tenant::getJdbcUsername, data.getJdbcUsername());

      validConnection = true;
    }
    if (StringUtil.isNotBlank(data.getJdbcPassword())) {
      updateWrapper.set(Tenant::getJdbcPassword, EncryptUtil.encrypt(data.getJdbcPassword()));

      validConnection = true;
    }

    this.update(updateWrapper);

    Tenant tenant = this.getById(data.getId());

    if (validConnection) {
      if (!DataSourceUtil.validConnection(tenant.getJdbcUrl(), tenant.getJdbcUsername(),
          EncryptUtil.decrypt(tenant.getJdbcPassword()))) {
        throw new DefaultClientException("数据库连接失败，请检查数据库配置！");
      }
    }
  }

  @Caching(evict = {
      @CacheEvict(value = Tenant.CACHE_NAME, key = "#key"),
      @CacheEvict(value = Tenant.CACHE_NAME, key = "'all'")
  })
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
