package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.entity.DefaultSysUserPosition;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
public interface DefaultSysUserPositionMapper extends BaseMapper<DefaultSysUserPosition> {

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<DefaultSysUserPosition> getByUserId(String userId);
}
