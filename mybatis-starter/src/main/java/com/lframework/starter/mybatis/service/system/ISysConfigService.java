package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.dto.system.config.SysConfigDto;
import com.lframework.starter.mybatis.entity.SysConfig;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.config.UpdateSysConfigVo;

/**
 * 系统设置 Service
 *
 * @author zmj
 */
public interface ISysConfigService extends BaseMpService<SysConfig> {

  /**
   * 根据ID查询
   *
   * @return
   */
  SysConfigDto get();

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysConfigVo vo);
}
