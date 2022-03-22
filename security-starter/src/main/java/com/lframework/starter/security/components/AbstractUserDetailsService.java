package com.lframework.starter.security.components;

import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.security.exception.NoPermissionException;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.service.IMenuService;
import com.lframework.starter.web.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 所有UserDetailsService都需要继承此类 如果需要更改用户表，那么继承此类注册Bean即可
 *
 * @author zmj
 */
public abstract class AbstractUserDetailsService implements UserDetailsService {

  @Autowired
  private IMenuService menuService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    //根据登录名查询
    AbstractUserDetails userDetails = this.findByUsername(username);

    if (ObjectUtil.isEmpty(userDetails)) {
      throw new UsernameNotFoundException("用户名或密码错误！");
    }

    //获取登录IP
    userDetails.setIp(RequestUtil.getRequestIp());

    userDetails.setPermissions(menuService.getPermissionsByUserId(userDetails.getId()));

    if (userDetails.isNoPermission()) {
      throw new NoPermissionException("用户无权限！");
    }

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
