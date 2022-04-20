package com.lframework.starter.mybatis.impl;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.events.UpdateUserEvent;
import com.lframework.starter.mybatis.mappers.DefaultUserMapper;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.components.security.PasswordEncoderWrapper;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.dto.UserInfoDto;
import com.lframework.starter.web.utils.ApplicationUtil;
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
    implements IUserService {

  @Autowired
  private PasswordEncoderWrapper encoderWrapper;

  @Cacheable(value = UserInfoDto.CACHE_NAME, key = "#userId", unless = "#result == null")
  @Override
  public UserInfoDto getInfo(@NonNull String userId) {

    return this.doGetInfo(userId);
  }

  @Transactional
  @Override
  public void updatePassword(@NonNull String userId, @NonNull String password) {

    this.doUpdatePassword(userId, this.encodePassword(password));

    IUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(userId);

    ApplicationUtil.publishEvent(new UpdateUserEvent(this, userId));
  }

  @Transactional
  @Override
  public void updateEmail(@NonNull String userId, @NonNull String email) {

    this.doUpdateEmail(userId, email);

    IUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(userId);

    ApplicationUtil.publishEvent(new UpdateUserEvent(this, userId));
  }

  @Transactional
  @Override
  public void updateTelephone(@NonNull String userId, @NonNull String telephone) {

    this.doUpdateTelephone(userId, telephone);

    IUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(userId);

    ApplicationUtil.publishEvent(new UpdateUserEvent(this, userId));
  }

  @Cacheable(value = UserDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public UserDto findById(String id) {

    if (StringUtil.isBlank(id)) {
      return null;
    }

    return this.doGetById(id);
  }

  @Transactional
  @Override
  public void lockById(String id) {

    getBaseMapper().lockById(id);

    IUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(id);
  }

  @Transactional
  @Override
  public void unlockById(String id) {

    getBaseMapper().unlockById(id);

    IUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(id);
  }

  @CacheEvict(value = {UserInfoDto.CACHE_NAME, UserDto.CACHE_NAME}, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

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
