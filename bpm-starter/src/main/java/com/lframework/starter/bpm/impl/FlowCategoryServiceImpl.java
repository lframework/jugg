package com.lframework.starter.bpm.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.mappers.FlowCategoryMapper;
import com.lframework.starter.bpm.service.FlowCategoryService;
import com.lframework.starter.bpm.vo.flow.category.CreateFlowCategoryVo;
import com.lframework.starter.bpm.vo.flow.category.UpdateFlowCategoryVo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import java.io.Serializable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowCategoryServiceImpl extends
    BaseMpServiceImpl<FlowCategoryMapper, FlowCategory> implements
    FlowCategoryService {

  @Cacheable(value = FlowCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public FlowCategory findById(String id) {
    return getById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateFlowCategoryVo vo) {
    FlowCategory record = new FlowCategory();
    record.setName(vo.getName());
    if (!StringUtil.isEmpty(vo.getParentId())) {
      record.setParentId(vo.getParentId());
    } else {
      record.setParentId(StringPool.EMPTY_STR);
    }
    this.save(record);
    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateFlowCategoryVo vo) {
    Wrapper<FlowCategory> updateWrapper = Wrappers.lambdaUpdate(FlowCategory.class)
        .set(FlowCategory::getName, vo.getName())
        .eq(FlowCategory::getId, vo.getId());
    this.update(updateWrapper);
  }

  @CacheEvict(value = FlowCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
