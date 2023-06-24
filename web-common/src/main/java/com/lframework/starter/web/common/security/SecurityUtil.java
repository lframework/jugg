package com.lframework.starter.web.common.security;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Spring Security工具类
 *
 * @author zmj
 */
@Slf4j
public class SecurityUtil {

  private static final ThreadLocal<AbstractUserDetails> LOGIN_POOL = new ThreadLocal<>();

  /**
   * 清除当前登录人 只能用于子线程的登录人信息传递
   */
  public static void removeCurrentUser() {
    LOGIN_POOL.remove();
  }

  /**
   * 获取当前登录用户信息
   *
   * @return
   */
  public static AbstractUserDetails getCurrentUser() {

    try {
      if (RequestContextHolder.currentRequestAttributes() == null) {
        // 非web环境
        return LOGIN_POOL.get();
      }
    } catch (Exception e) {
      // 非web环境
      return LOGIN_POOL.get();
    }

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

  /**
   * 设置当前登录人 只能用于子线程的登录人信息传递 如果使用此方法，需要确保子线程最终会调用removeCurrentUser！否则登录人信息会混乱！
   *
   * @param user
   */
  public static void setCurrentUser(AbstractUserDetails user) {
    LOGIN_POOL.set(user);
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

  /**
   * 获取当前登录人租户ID
   *
   * @return
   */
  public static Integer getCurrentTenantId() {
    AbstractUserDetails currentUser = getCurrentUser();
    return currentUser == null ? null : currentUser.getTenantId();
  }

  public static AbstractUserDetails getUserByToken(String token) {
    if (token == null) {
      return null;
    }

    try {
      Object loginId = StpUtil.getLoginIdByToken(token);
      if (loginId == null) {
        return null;
      }
      SaSession session = StpUtil.getSessionByLoginId(loginId);
      if (session == null) {
        return null;
      }
      return (AbstractUserDetails) session.get(SecurityConstants.USER_INFO_KEY);
    } catch (SaTokenException e) {
      return null;
    }
  }
}
