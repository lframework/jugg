package com.lframework.starter.security.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.web.components.security.DefaultUserDetails;

/**
 * 用于登录的用户信息查询Mapper
 *
 * @author zmj
 */
public interface DefaultUserDetailsMapper extends BaseMapper {

  /**
   * 根据登录名查询
   *
   * @param username
   * @return
   */
  DefaultUserDetails findByUsername(String username);
}
