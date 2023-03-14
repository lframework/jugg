package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.DefaultSysPosition;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysPositionMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.SysPositionService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.position.CreateSysPositionVo;
import com.lframework.starter.mybatis.vo.system.position.QuerySysPositionVo;
import com.lframework.starter.mybatis.vo.system.position.SysPositionSelectorVo;
import com.lframework.starter.mybatis.vo.system.position.UpdateSysPositionVo;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysPositionServiceImpl extends
    BaseMpServiceImpl<DefaultSysPositionMapper, DefaultSysPosition>
    implements SysPositionService {

  @Override
  public PageResult<DefaultSysPosition> query(Integer pageIndex, Integer pageSize,
      QuerySysPositionVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<DefaultSysPosition> datas = this.doQuery(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = DefaultSysPosition.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public DefaultSysPosition findById(String id) {

    return this.doGetById(id);
  }

  @Override
  public PageResult<DefaultSysPosition> selector(Integer pageIndex, Integer pageSize,
      SysPositionSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<DefaultSysPosition> datas = this.doSelector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "停用岗位，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchUnable(ids);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "启用岗位，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchEnable(ids);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增岗位，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysPositionVo vo) {

    DefaultSysPosition data = this.doCreate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改岗位，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysPositionVo vo) {

    this.doUpdate(vo);

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  protected List<DefaultSysPosition> doQuery(QuerySysPositionVo vo) {

    return getBaseMapper().query(vo);
  }

  protected DefaultSysPosition doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected List<DefaultSysPosition> doSelector(SysPositionSelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  protected void doBatchUnable(Collection<String> ids) {

    Wrapper<DefaultSysPosition> updateWrapper = Wrappers.lambdaUpdate(DefaultSysPosition.class)
        .set(DefaultSysPosition::getAvailable, Boolean.FALSE).in(DefaultSysPosition::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected void doBatchEnable(Collection<String> ids) {

    Wrapper<DefaultSysPosition> updateWrapper = Wrappers.lambdaUpdate(DefaultSysPosition.class)
        .set(DefaultSysPosition::getAvailable, Boolean.TRUE).in(DefaultSysPosition::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected DefaultSysPosition doCreate(CreateSysPositionVo vo) {

    Wrapper<DefaultSysPosition> checkWrapper = Wrappers.lambdaQuery(DefaultSysPosition.class)
        .eq(DefaultSysPosition::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(DefaultSysPosition.class)
        .eq(DefaultSysPosition::getName, vo.getName());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    DefaultSysPosition data = new DefaultSysPosition();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());

    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    return data;
  }

  protected void doUpdate(UpdateSysPositionVo vo) {

    DefaultSysPosition data = this.findById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("岗位不存在！");
    }

    Wrapper<DefaultSysPosition> checkWrapper = Wrappers.lambdaQuery(DefaultSysPosition.class)
        .eq(DefaultSysPosition::getCode, vo.getCode()).ne(DefaultSysPosition::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(DefaultSysPosition.class)
        .eq(DefaultSysPosition::getName, vo.getName())
        .ne(DefaultSysPosition::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<DefaultSysPosition> updateWrapper = Wrappers.lambdaUpdate(
            DefaultSysPosition.class)
        .set(DefaultSysPosition::getCode, vo.getCode())
        .set(DefaultSysPosition::getName, vo.getName())
        .set(DefaultSysPosition::getAvailable, vo.getAvailable())
        .set(DefaultSysPosition::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(DefaultSysPosition::getId, vo.getId());

    getBaseMapper().update(updateWrapper);
  }

  @CacheEvict(value = DefaultSysPosition.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
