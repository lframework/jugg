package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.starter.mybatis.entity.SysDataDicItem;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.SysDataDicItemMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.ISysDataDicItemService;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.dic.item.CreateSysDataDicItemVo;
import com.lframework.starter.mybatis.vo.system.dic.item.QuerySysDataDicItemVo;
import com.lframework.starter.mybatis.vo.system.dic.item.UpdateSysDataDicItemVo;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysDataDicItemServiceImpl extends
    BaseMpServiceImpl<SysDataDicItemMapper, SysDataDicItem> implements
    ISysDataDicItemService {

  @Override
  public PageResult<SysDataDicItem> query(Integer pageIndex, Integer pageSize,
      QuerySysDataDicItemVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysDataDicItem> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysDataDicItem> query(QuerySysDataDicItemVo vo) {
    return getBaseMapper().query(vo);
  }

  @Cacheable(value = SysDataDicItem.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public SysDataDicItem findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional
  @Override
  public String create(CreateSysDataDicItemVo vo) {

    Wrapper<SysDataDicItem> checkWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDataDicItem record = new SysDataDicItem();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());
    record.setDicId(vo.getDicId());
    record.setOrderNo(vo.getOrderNo());

    this.save(record);

    return record.getId();
  }

  @Transactional
  @Override
  public void update(UpdateSysDataDicItemVo vo) {
    Wrapper<SysDataDicItem> checkWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getCode, vo.getCode()).ne(SysDataDicItem::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getName, vo.getName()).ne(SysDataDicItem::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDataDicItem record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("数据字典不存在！");
    }

    Wrapper<SysDataDicItem> updateWrapper = Wrappers.lambdaUpdate(SysDataDicItem.class)
        .set(SysDataDicItem::getCode, vo.getCode()).set(SysDataDicItem::getName, vo.getName())
        .set(SysDataDicItem::getOrderNo, vo.getOrderNo())
        .eq(SysDataDicItem::getId, vo.getId());
    this.update(updateWrapper);
  }

  @Transactional
  @Override
  public void deleteById(String id) {
    this.removeById(id);
  }

  @CacheEvict(value = SysDataDicItem.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
