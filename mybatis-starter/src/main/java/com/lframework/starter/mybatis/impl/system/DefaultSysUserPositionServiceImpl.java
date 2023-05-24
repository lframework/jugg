package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.DefaultSysUserPosition;
 import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysUserPositionMapper;
import com.lframework.starter.mybatis.service.system.SysUserPositionService;
import com.lframework.starter.mybatis.vo.system.position.SysUserPositionSettingVo;
import com.lframework.starter.web.utils.IdUtil;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysUserPositionServiceImpl
    extends BaseMpServiceImpl<DefaultSysUserPositionMapper, DefaultSysUserPosition>
    implements SysUserPositionService {

  @OpLog(type = DefaultOpLogType.OTHER, name = "用户设置岗位，用户ID：{}，岗位ID：{}", params = {"#vo.userId",
      "#vo.positionIds"}, loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysUserPositionSettingVo vo) {

    this.doSetting(vo);
  }

  @Override
  public List<DefaultSysUserPosition> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  protected void doSetting(SysUserPositionSettingVo vo) {

    Wrapper<DefaultSysUserPosition> deleteWrapper = Wrappers.lambdaQuery(
            DefaultSysUserPosition.class)
        .eq(DefaultSysUserPosition::getUserId, vo.getUserId());
    getBaseMapper().delete(deleteWrapper);

    if (!CollectionUtil.isEmpty(vo.getPositionIds())) {
      for (String positionId : vo.getPositionIds()) {
        DefaultSysUserPosition record = new DefaultSysUserPosition();
        record.setId(IdUtil.getId());
        record.setUserId(vo.getUserId());
        record.setPositionId(positionId);

        getBaseMapper().insert(record);
      }
    }
  }

  protected List<DefaultSysUserPosition> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }
}
