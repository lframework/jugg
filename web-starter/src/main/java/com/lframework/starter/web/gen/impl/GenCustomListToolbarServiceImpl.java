package com.lframework.starter.web.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.gen.entity.GenCustomListToolbar;
import com.lframework.starter.web.gen.mappers.GenCustomListToolbarMapper;
import com.lframework.starter.web.gen.service.GenCustomListToolbarService;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomListToolbarServiceImpl extends
    BaseMpServiceImpl<GenCustomListToolbarMapper, GenCustomListToolbar> implements
    GenCustomListToolbarService {

  @Override
  public List<GenCustomListToolbar> getByCustomListId(String customListId) {
    return getBaseMapper().getByCustomListId(customListId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByCustomListId(String customListId) {

    Wrapper<GenCustomListToolbar> deleteWrapper = Wrappers.lambdaQuery(GenCustomListToolbar.class)
        .eq(GenCustomListToolbar::getCustomListId, customListId);
    this.remove(deleteWrapper);
  }
}
