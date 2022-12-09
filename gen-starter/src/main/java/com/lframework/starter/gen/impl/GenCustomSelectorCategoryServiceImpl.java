package com.lframework.starter.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.starter.gen.entity.GenCustomSelector;
import com.lframework.starter.gen.entity.GenCustomSelectorCategory;
import com.lframework.starter.gen.mappers.GenCustomSelectorCategoryMapper;
import com.lframework.starter.gen.service.IGenCustomSelectorCategoryService;
import com.lframework.starter.gen.service.IGenCustomSelectorService;
import com.lframework.starter.gen.vo.custom.selector.category.CreateGenCustomSelectorCategoryVo;
import com.lframework.starter.gen.vo.custom.selector.category.GenCustomSelectorCategorySelectorVo;
import com.lframework.starter.gen.vo.custom.selector.category.UpdateGenCustomSelectorCategoryVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomSelectorCategoryServiceImpl extends
    BaseMpServiceImpl<GenCustomSelectorCategoryMapper, GenCustomSelectorCategory> implements
    IGenCustomSelectorCategoryService {

  @Autowired
  private IGenCustomSelectorService genCustomSelectorService;

  @Cacheable(value = GenCustomSelectorCategory.CACHE_NAME, key = "'all'")
  @Override
  public List<GenCustomSelectorCategory> queryList() {
    return getBaseMapper().query();
  }

  @Override
  public PageResult<GenCustomSelectorCategory> selector(Integer pageIndex, Integer pageSize,
      GenCustomSelectorCategorySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<GenCustomSelectorCategory> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenCustomSelectorCategory.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public GenCustomSelectorCategory findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional
  @Override
  public String create(CreateGenCustomSelectorCategoryVo vo) {

    Wrapper<GenCustomSelectorCategory> checkWrapper = Wrappers
        .lambdaQuery(GenCustomSelectorCategory.class)
        .eq(GenCustomSelectorCategory::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenCustomSelectorCategory.class)
        .eq(GenCustomSelectorCategory::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenCustomSelectorCategory record = new GenCustomSelectorCategory();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.save(record);

    return record.getId();
  }

  @Transactional
  @Override
  public void update(UpdateGenCustomSelectorCategoryVo vo) {
    Wrapper<GenCustomSelectorCategory> checkWrapper = Wrappers
        .lambdaQuery(GenCustomSelectorCategory.class)
        .eq(GenCustomSelectorCategory::getCode, vo.getCode())
        .ne(GenCustomSelectorCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenCustomSelectorCategory.class)
        .eq(GenCustomSelectorCategory::getName, vo.getName())
        .ne(GenCustomSelectorCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenCustomSelectorCategory record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("自定义列表分类不存在！");
    }

    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.updateById(record);
  }

  @Transactional
  @Override
  public void deleteById(String id) {

    Wrapper<GenCustomSelector> queryWrapper = Wrappers.lambdaQuery(GenCustomSelector.class)
        .eq(GenCustomSelector::getCategoryId, id);
    if (genCustomSelectorService.count(queryWrapper) > 0) {
      throw new DefaultClientException("此分类下存在自定义选择器，无法删除！");
    }

    this.removeById(id);
  }

  @CacheEvict(value = GenCustomSelectorCategory.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}