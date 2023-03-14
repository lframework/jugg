package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.entity.DefaultSysUserPosition;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.position.SysUserPositionSettingVo;
import java.util.List;

public interface SysUserPositionService extends BaseMpService<DefaultSysUserPosition> {

  /**
   * 设置岗位
   *
   * @param vo
   */
  void setting(SysUserPositionSettingVo vo);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<DefaultSysUserPosition> getByUserId(String userId);
}
