package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.starter.mybatis.entity.SysDataDic;
import com.lframework.starter.mybatis.entity.SysDataDicCategory;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.SysDataDicCategoryMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.ISysDataDicCategoryService;
import com.lframework.starter.mybatis.service.system.ISysDataDicService;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.dic.category.CreateSysDataDicCategoryVo;
import com.lframework.starter.mybatis.vo.system.dic.category.SysDataDicCategorySelectorVo;
import com.lframework.starter.mybatis.vo.system.dic.category.UpdateSysDataDicCategoryVo;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysDataDicCategoryServiceImpl extends
    BaseMpServiceImpl<SysDataDicCategoryMapper, SysDataDicCategory> implements
    ISysDataDicCategoryService {

  @Autowired
  private ISysDataDicService sysDataDicService;

  @Cacheable(value = SysDataDicCategory.CACHE_NAME, key = "'all'")
  @Override
  public List<SysDataDicCategory> queryList() {
    return getBaseMapper().query();
  }

  @Override
  public PageResult<SysDataDicCategory> selector(Integer pageIndex, Integer pageSize,
      SysDataDicCategorySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysDataDicCategory> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = SysDataDicCategory.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public SysDataDicCategory findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional
  @Override
  public String create(CreateSysDataDicCategoryVo vo) {

    Wrapper<SysDataDicCategory> checkWrapper = Wrappers.lambdaQuery(SysDataDicCategory.class)
        .eq(SysDataDicCategory::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDicCategory.class)
        .eq(SysDataDicCategory::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDataDicCategory record = new SysDataDicCategory();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.save(record);

    return record.getId();
  }

  @Transactional
  @Override
  public void update(UpdateSysDataDicCategoryVo vo) {
    Wrapper<SysDataDicCategory> checkWrapper = Wrappers.lambdaQuery(SysDataDicCategory.class)
        .eq(SysDataDicCategory::getCode, vo.getCode()).ne(SysDataDicCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDicCategory.class)
        .eq(SysDataDicCategory::getName, vo.getName()).ne(SysDataDicCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDataDicCategory record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("数据字典分类不存在！");
    }

    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.updateById(record);
  }

  @Transactional
  @Override
  public void deleteById(String id) {

    Wrapper<SysDataDic> queryDicWrapper = Wrappers.lambdaQuery(SysDataDic.class)
        .eq(SysDataDic::getCategoryId, id);
    if (sysDataDicService.count(queryDicWrapper) > 0) {
      throw new DefaultClientException("此分类下存在数据字典，无法删除！");
    }

    this.removeById(id);
  }

  @CacheEvict(value = SysDataDicCategory.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
