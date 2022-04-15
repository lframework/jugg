package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.dto.system.position.DefaultSysUserPositionDto;
import com.lframework.starter.mybatis.vo.system.position.SysUserPositionSettingVo;
import com.lframework.starter.web.service.BaseService;
import java.util.List;

public interface ISysUserPositionService extends BaseService {

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
  List<DefaultSysUserPositionDto> getByUserId(String userId);
}
