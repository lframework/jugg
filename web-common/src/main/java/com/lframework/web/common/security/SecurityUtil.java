package com.lframework.web.common.security;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

/**
 * Spring Security工具类
 *
 * @author zmj
 */
@Slf4j
public class SecurityUtil {

  /**
   * 获取当前登录用户信息
   *
   * @return
   */
  public static AbstractUserDetails getCurrentUser() {

    try {
      SaSession session = StpUtil.getSession(false);
      if (session == null) {
        return null;
      }
      return (AbstractUserDetails) session.get(SecurityConstants.USER_INFO_KEY);
    } catch (SaTokenException e) {
      return null;
    }
  }

  public static AbstractUserDetails getCurrentUser(Authentication authentication) {

    return getCurrentUser();
  }

  /**
   * 手动退出登录
   */
  public static void logout() {

    StpUtil.logout();
  }
}
