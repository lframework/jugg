package com.lframework.starter.bpm.listeners;

import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.security.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.security.events.UpdateUserEvent;
import com.lframework.starter.security.service.system.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BpmUpdateUserListener implements ApplicationListener<UpdateUserEvent> {

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private ISysUserService sysUserService;

  private static final String loginUserCacheKey = "agilebpm:loginUser:";

  @Override
  public void onApplicationEvent(UpdateUserEvent updateUserEvent) {
    // 清除BPM登录用户缓存信息
    DefaultSysUserDto user = sysUserService.getById(updateUserEvent.getId());
    redisHandler.del(loginUserCacheKey.concat(user.getUsername()));
  }
}
