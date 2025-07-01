package com.lframework.starter.web.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.gen.entity.GenCustomListQueryParams;
import com.lframework.starter.web.gen.mappers.GenCustomListQueryParamsMapper;
import com.lframework.starter.web.gen.service.GenCustomListQueryParamsService;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomListQueryParamsServiceImpl extends
    BaseMpServiceImpl<GenCustomListQueryParamsMapper, GenCustomListQueryParams> implements
    GenCustomListQueryParamsService {

  @Override
  public List<GenCustomListQueryParams> getByCustomListId(String customListId) {
    Wrapper<GenCustomListQueryParams> queryWrapper = Wrappers.lambdaQuery(
            GenCustomListQueryParams.class).eq(GenCustomListQueryParams::getCustomListId, customListId)
        .orderByAsc(GenCustomListQueryParams::getOrderNo);
    return this.list(queryWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByCustomListId(String customListId) {
    Wrapper<GenCustomListQueryParams> deleteWrapper = Wrappers.lambdaQuery(
            GenCustomListQueryParams.class)
        .eq(GenCustomListQueryParams::getCustomListId, customListId);
    this.remove(deleteWrapper);
  }
}
