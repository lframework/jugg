package com.lframework.starter.web.inner.impl.system;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.entity.SysUserMenuSort;
import com.lframework.starter.web.inner.mappers.system.SysUserMenuSortMapper;
import com.lframework.starter.web.inner.service.system.SysUserMenuSortService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户菜单排序偏好 Service 实现
 *
 * @author lframework@163.com
 */
@Service
public class SysUserMenuSortServiceImpl extends
    BaseMpServiceImpl<SysUserMenuSortMapper, SysUserMenuSort> implements SysUserMenuSortService {

  @Override
  public List<SysUserMenuSort> getByUserId(String userId) {

    if (StringUtil.isBlank(userId)) {
      return CollectionUtil.emptyList();
    }

    return list(Wrappers.lambdaQuery(SysUserMenuSort.class)
        .eq(SysUserMenuSort::getUserId, userId)
        .orderByAsc(SysUserMenuSort::getParentId)
        .orderByAsc(SysUserMenuSort::getSortNo));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void replaceUserSorts(String userId, List<SysUserMenuSort> records) {

    if (StringUtil.isBlank(userId)) {
      return;
    }

    remove(Wrappers.lambdaQuery(SysUserMenuSort.class)
        .eq(SysUserMenuSort::getUserId, userId));

    if (CollectionUtil.isEmpty(records)) {
      return;
    }

    records.forEach(record -> {
      if (StringUtil.isBlank(record.getId())) {
        record.setId(IdUtil.getId());
      }
    });

    saveBatch(records);
  }
}
