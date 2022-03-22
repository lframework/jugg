package com.lframework.starter.security.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.dto.UserInfoDto;
import org.apache.ibatis.annotations.Param;

/**
 * 默认UserMapper
 *
 * @author zmj
 */
public interface DefaultUserMapper extends BaseMapper {

  /**
   * 获取用户信息
   *
   * @param userId 用户ID
   * @return
   */
  UserInfoDto getInfo(String userId);

  /**
   * 修改用户密码
   *
   * @param userId   用户ID
   * @param password 新密码（密文）
   */
  void updatePassword(@Param("userId") String userId, @Param("password") String password);

  /**
   * 修改邮箱
   *
   * @param userId 用户ID
   * @param email  邮箱
   */
  void updateEmail(@Param("userId") String userId, @Param("email") String email);

  /**
   * 修改联系电话
   *
   * @param userId    用户ID
   * @param telephone 联系电话
   */
  void updateTelephone(@Param("userId") String userId, @Param("telephone") String telephone);

  /**
   * 根据ID查询 主要用于各个业务关联查询用户信息
   *
   * @param id
   * @return
   */
  UserDto getById(String id);

  /**
   * 根据ID锁定
   *
   * @param id
   */
  void lockById(String id);

  /**
   * 根据ID解锁
   *
   * @param id
   */
  void unlockById(String id);
}
