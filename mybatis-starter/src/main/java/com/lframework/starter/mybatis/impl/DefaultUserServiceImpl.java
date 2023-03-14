package com.lframework.starter.mybatis.impl;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.mappers.DefaultUserMapper;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.components.security.PasswordEncoderWrapper;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.dto.UserInfoDto;
import java.io.Serializable;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

/**
 * 默认用户Service实现
 *
 * @author zmj
 */
public class DefaultUserServiceImpl extends BaseMpServiceImpl<DefaultUserMapper, DefaultSysUser>
    implements UserService {

  @Autowired
  private PasswordEncoderWrapper encoderWrapper;

  @Cacheable(value = UserInfoDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #userId", unless = "#result == null")
  @Override
  public UserInfoDto getInfo(@NonNull String userId) {

    return this.doGetInfo(userId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updatePassword(@NonNull String userId, @NonNull String password) {

    this.doUpdatePassword(userId, this.encodePassword(password));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateEmail(@NonNull String userId, @NonNull String email) {

    this.doUpdateEmail(userId, email);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateTelephone(@NonNull String userId, @NonNull String telephone) {

    this.doUpdateTelephone(userId, telephone);
  }

  @Cacheable(value = UserDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public UserDto findById(String id) {

    if (StringUtil.isBlank(id)) {
      return null;
    }

    return this.doGetById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void lockById(String id) {

    getBaseMapper().lockById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void unlockById(String id) {

    getBaseMapper().unlockById(id);
  }

  @CacheEvict(value = {UserInfoDto.CACHE_NAME, UserDto.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }

  protected UserInfoDto doGetInfo(@NonNull String userId) {

    return getBaseMapper().getInfo(userId);
  }

  protected void doUpdatePassword(@NonNull String userId, @NonNull String password) {

    getBaseMapper().updatePassword(userId, password);
  }

  protected void doUpdateEmail(@NonNull String userId, @NonNull String email) {

    getBaseMapper().updateEmail(userId, email);
  }

  protected void doUpdateTelephone(@NonNull String userId, @NonNull String telephone) {

    getBaseMapper().updateTelephone(userId, telephone);
  }

  protected UserDto doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected String encodePassword(String password) {

    return encoderWrapper.getEncoder().encode(password);
  }
}
