package com.lframework.starter.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.gen.entity.GenCustomListHandleColumn;
import com.lframework.starter.gen.mappers.GenCustomListHandleColumnMapper;
import com.lframework.starter.gen.service.IGenCustomListHandleColumnService;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomListHandleColumnServiceImpl extends
    BaseMpServiceImpl<GenCustomListHandleColumnMapper, GenCustomListHandleColumn> implements
    IGenCustomListHandleColumnService {

  @Override
  public List<GenCustomListHandleColumn> getByCustomListId(String customListId) {
    return getBaseMapper().getByCustomListId(customListId);
  }

  @Transactional
  @Override
  public void deleteByCustomListId(String customListId) {

    Wrapper<GenCustomListHandleColumn> deleteWrapper = Wrappers
        .lambdaQuery(GenCustomListHandleColumn.class)
        .eq(GenCustomListHandleColumn::getCustomListId, customListId);
    this.remove(deleteWrapper);
  }
}
