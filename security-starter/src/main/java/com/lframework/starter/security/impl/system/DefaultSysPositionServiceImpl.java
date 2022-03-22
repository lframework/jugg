package com.lframework.starter.security.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.security.entity.DefaultSysPosition;
import com.lframework.starter.security.mappers.system.DefaultSysPositionMapper;
import com.lframework.starter.security.service.system.ISysPositionService;
import com.lframework.starter.security.vo.system.position.CreateSysPositionVo;
import com.lframework.starter.security.vo.system.position.QuerySysPositionVo;
import com.lframework.starter.security.vo.system.position.SysPositionSelectorVo;
import com.lframework.starter.security.vo.system.position.UpdateSysPositionVo;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysPositionServiceImpl implements ISysPositionService {

  @Autowired
  private DefaultSysPositionMapper defaultSysPositionMapper;

  @Override
  public PageResult<DefaultSysPositionDto> query(Integer pageIndex, Integer pageSize,
      QuerySysPositionVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<DefaultSysPositionDto> datas = this.doQuery(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = DefaultSysPositionDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public DefaultSysPositionDto getById(String id) {

    return this.doGetById(id);
  }

  @Override
  public PageResult<DefaultSysPositionDto> selector(Integer pageIndex, Integer pageSize,
      SysPositionSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<DefaultSysPositionDto> datas = this.doSelector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = OpLogType.OTHER, name = "停用岗位，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchUnable(ids);

    ISysPositionService thisService = getThis(this.getClass());
    for (String id : ids) {
      thisService.cleanCacheByKey(id);
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "启用岗位，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchEnable(ids);

    ISysPositionService thisService = getThis(this.getClass());
    for (String id : ids) {
      thisService.cleanCacheByKey(id);
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "新增岗位，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateSysPositionVo vo) {

    DefaultSysPosition data = this.doCreate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改岗位，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateSysPositionVo vo) {

    this.doUpdate(vo);

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    ISysPositionService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(vo.getId());
  }

  protected List<DefaultSysPositionDto> doQuery(QuerySysPositionVo vo) {

    return defaultSysPositionMapper.query(vo);
  }

  protected DefaultSysPositionDto doGetById(String id) {

    return defaultSysPositionMapper.getById(id);
  }

  protected List<DefaultSysPositionDto> doSelector(SysPositionSelectorVo vo) {

    return defaultSysPositionMapper.selector(vo);
  }

  protected void doBatchUnable(Collection<String> ids) {

    Wrapper<DefaultSysPosition> updateWrapper = Wrappers.lambdaUpdate(DefaultSysPosition.class)
        .set(DefaultSysPosition::getAvailable, Boolean.FALSE).in(DefaultSysPosition::getId, ids);
    defaultSysPositionMapper.update(updateWrapper);
  }

  protected void doBatchEnable(Collection<String> ids) {

    Wrapper<DefaultSysPosition> updateWrapper = Wrappers.lambdaUpdate(DefaultSysPosition.class)
        .set(DefaultSysPosition::getAvailable, Boolean.TRUE).in(DefaultSysPosition::getId, ids);
    defaultSysPositionMapper.update(updateWrapper);
  }

  protected DefaultSysPosition doCreate(CreateSysPositionVo vo) {

    Wrapper<DefaultSysPosition> checkWrapper = Wrappers.lambdaQuery(DefaultSysPosition.class)
        .eq(DefaultSysPosition::getCode, vo.getCode());
    if (defaultSysPositionMapper.selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(DefaultSysPosition.class)
        .eq(DefaultSysPosition::getName, vo.getName());
    if (defaultSysPositionMapper.selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    DefaultSysPosition data = new DefaultSysPosition();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());

    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    defaultSysPositionMapper.insert(data);

    return data;
  }

  protected void doUpdate(UpdateSysPositionVo vo) {

    DefaultSysPositionDto data = this.getById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("岗位不存在！");
    }

    Wrapper<DefaultSysPosition> checkWrapper = Wrappers.lambdaQuery(DefaultSysPosition.class)
        .eq(DefaultSysPosition::getCode, vo.getCode()).ne(DefaultSysPosition::getId, vo.getId());
    if (defaultSysPositionMapper.selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(DefaultSysPosition.class)
        .eq(DefaultSysPosition::getName, vo.getName()).ne(DefaultSysPosition::getId, vo.getId());
    if (defaultSysPositionMapper.selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<DefaultSysPosition> updateWrapper = Wrappers
        .lambdaUpdate(DefaultSysPosition.class)
        .set(DefaultSysPosition::getCode, vo.getCode())
        .set(DefaultSysPosition::getName, vo.getName())
        .set(DefaultSysPosition::getAvailable, vo.getAvailable())
        .set(DefaultSysPosition::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(DefaultSysPosition::getId, vo.getId());

    defaultSysPositionMapper.update(updateWrapper);
  }

  @CacheEvict(value = DefaultSysPositionDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}
