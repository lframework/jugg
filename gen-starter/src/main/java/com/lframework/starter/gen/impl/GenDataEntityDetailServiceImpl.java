package com.lframework.starter.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.mappers.GenDataEntityDetailMapper;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.vo.data.entity.GenDataEntityDetailSelectorVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenDataEntityDetailServiceImpl extends
    BaseMpServiceImpl<GenDataEntityDetailMapper, GenDataEntityDetail> implements
    IGenDataEntityDetailService {

  @Override
  public List<GenDataEntityDetail> getByEntityId(String entityId) {
    Wrapper<GenDataEntityDetail> queryWrapper = Wrappers.lambdaQuery(GenDataEntityDetail.class)
        .eq(GenDataEntityDetail::getEntityId, entityId)
        .orderByAsc(GenDataEntityDetail::getColumnOrder);

    return getBaseMapper().selectList(queryWrapper);
  }

  @Transactional
  @Override
  public void deleteByEntityId(String entityId) {
    Wrapper<GenDataEntityDetail> queryWrapper = Wrappers.lambdaQuery(GenDataEntityDetail.class)
        .eq(GenDataEntityDetail::getEntityId, entityId);
    getBaseMapper().delete(queryWrapper);
  }

  @Override
  public List<GenDataEntityDetail> selector(GenDataEntityDetailSelectorVo vo) {
    Wrapper<GenDataEntityDetail> queryWrapper = Wrappers.lambdaQuery(GenDataEntityDetail.class)
        .eq(GenDataEntityDetail::getEntityId, vo.getEntityId())
        .orderByAsc(GenDataEntityDetail::getColumnOrder);

    return getBaseMapper().selectList(queryWrapper);
  }
}
