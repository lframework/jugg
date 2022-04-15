package com.lframework.starter.mybatis.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.dto.system.config.SysConfigDto;
import com.lframework.starter.mybatis.entity.SysConfig;

/**
 * <p>
 * 系统设置 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface DefaultSysConfigMapper extends BaseMapper<SysConfig> {

  /**
   * 根据ID查询
   */
  SysConfigDto get();
}
