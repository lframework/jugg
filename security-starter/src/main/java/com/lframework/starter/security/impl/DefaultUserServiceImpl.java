package com.lframework.starter.security.impl;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.components.PasswordEncoderWrapper;
import com.lframework.starter.security.events.UpdateUserEvent;
import com.lframework.starter.security.mappers.DefaultUserMapper;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.dto.UserInfoDto;
import com.lframework.starter.web.service.IUserService;
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
public class DefaultUserServiceImpl implements IUserService {

  @Autowired
  private DefaultUserMapper userMapper;

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
  public UserDto getById(String id) {

    if (StringUtil.isBlank(id)) {
      return null;
    }

    return this.doGetById(id);
  }

  @Transactional
  @Override
  public void lockById(String id) {

    userMapper.lockById(id);

    IUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(id);
  }

  @Transactional
  @Override
  public void unlockById(String id) {
    userMapper.unlockById(id);

    IUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(id);
  }

  @CacheEvict(value = {UserInfoDto.CACHE_NAME, UserDto.CACHE_NAME}, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }

  protected UserInfoDto doGetInfo(@NonNull String userId) {

    return userMapper.getInfo(userId);
  }

  protected void doUpdatePassword(@NonNull String userId, @NonNull String password) {

    userMapper.updatePassword(userId, password);
  }

  protected void doUpdateEmail(@NonNull String userId, @NonNull String email) {

    userMapper.updateEmail(userId, email);
  }

  protected void doUpdateTelephone(@NonNull String userId, @NonNull String telephone) {

    userMapper.updateTelephone(userId, telephone);
  }

  protected UserDto doGetById(String id) {

    return userMapper.getById(id);
  }

  protected String encodePassword(String password) {

    return encoderWrapper.getEncoder().encode(password);
  }
}
