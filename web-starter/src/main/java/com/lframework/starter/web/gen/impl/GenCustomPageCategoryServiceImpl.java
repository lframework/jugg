package com.lframework.starter.web.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.gen.entity.GenCustomPage;
import com.lframework.starter.web.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.gen.mappers.GenCustomPageCategoryMapper;
import com.lframework.starter.web.gen.service.GenCustomPageCategoryService;
import com.lframework.starter.web.gen.service.GenCustomPageService;
import com.lframework.starter.web.gen.vo.custom.page.category.CreateGenCustomPageCategoryVo;
import com.lframework.starter.web.gen.vo.custom.page.category.GenCustomPageCategorySelectorVo;
import com.lframework.starter.web.gen.vo.custom.page.category.UpdateGenCustomPageCategoryVo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomPageCategoryServiceImpl extends
    BaseMpServiceImpl<GenCustomPageCategoryMapper, GenCustomPageCategory> implements
    GenCustomPageCategoryService {

  @Autowired
  private GenCustomPageService genCustomPageService;

  @Cacheable(value = GenCustomPageCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + 'all'")
  @Override
  public List<GenCustomPageCategory> queryList() {
    return getBaseMapper().query();
  }

  @Override
  public PageResult<GenCustomPageCategory> selector(Integer pageIndex, Integer pageSize,
      GenCustomPageCategorySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<GenCustomPageCategory> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenCustomPageCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public GenCustomPageCategory findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateGenCustomPageCategoryVo vo) {

    Wrapper<GenCustomPageCategory> checkWrapper = Wrappers
        .lambdaQuery(GenCustomPageCategory.class)
        .eq(GenCustomPageCategory::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenCustomPageCategory.class)
        .eq(GenCustomPageCategory::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenCustomPageCategory record = new GenCustomPageCategory();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());
    record.setParentId(StringUtil.isBlank(vo.getParentId()) ? null : vo.getParentId());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateGenCustomPageCategoryVo vo) {
    Wrapper<GenCustomPageCategory> checkWrapper = Wrappers
        .lambdaQuery(GenCustomPageCategory.class)
        .eq(GenCustomPageCategory::getCode, vo.getCode())
        .ne(GenCustomPageCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenCustomPageCategory.class)
        .eq(GenCustomPageCategory::getName, vo.getName())
        .ne(GenCustomPageCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenCustomPageCategory record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("自定义列表分类不存在！");
    }

    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.updateById(record);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {
    Set<String> categoryIds = new HashSet<>();
    categoryIds.add(id);
    while (true) {
      Wrapper<GenCustomPageCategory> queryCategoryWrapper = Wrappers.lambdaQuery(
          GenCustomPageCategory.class).in(GenCustomPageCategory::getParentId, categoryIds);
      List<GenCustomPageCategory> categoryList = this.list(queryCategoryWrapper);

      int oldSize = categoryIds.size();
      categoryIds.addAll(
          categoryList.stream().map(GenCustomPageCategory::getId).collect(Collectors.toList()));
      if (oldSize == categoryIds.size()) {
        break;
      }
    }

    Wrapper<GenCustomPage> queryWrapper = Wrappers.lambdaQuery(GenCustomPage.class)
        .in(GenCustomPage::getCategoryId, categoryIds);
    if (genCustomPageService.count(queryWrapper) > 0) {
      throw new DefaultClientException("此分类或其子分类下存在自定义页面，无法删除！");
    }

    this.removeByIds(categoryIds);
  }

  @CacheEvict(value = GenCustomPageCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
