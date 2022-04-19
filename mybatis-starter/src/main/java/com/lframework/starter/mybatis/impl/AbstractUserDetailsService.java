package com.lframework.starter.mybatis.impl;

import com.lframework.common.exceptions.impl.UserLoginException;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.service.IMenuService;
import com.lframework.starter.web.components.security.UserDetailsService;
import com.lframework.starter.web.utils.RequestUtil;
import com.lframework.web.common.security.AbstractUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 所有UserDetailsService都需要继承此类 如果需要更改用户表，那么继承此类注册Bean即可
 *
 * @author zmj
 */
@Slf4j
public abstract class AbstractUserDetailsService implements UserDetailsService {

  @Autowired
  private IMenuService menuService;

  @Override
  public AbstractUserDetails loadUserByUsername(String username) throws UserLoginException {

    //根据登录名查询
    AbstractUserDetails userDetails = this.findByUsername(username);

    if (ObjectUtil.isEmpty(userDetails)) {
      log.debug("用户名 {} 不存在", username);
      throw new UserLoginException("用户名或密码错误！");
    }

    //获取登录IP
    userDetails.setIp(RequestUtil.getRequestIp());

    userDetails.setPermissions(menuService.getPermissionsByUserId(userDetails.getId()));

    return userDetails;
  }

  /**
   * 根据登录名查询
   *
   * @param username
   * @return
   */
  public abstract AbstractUserDetails findByUsername(String username);
}
