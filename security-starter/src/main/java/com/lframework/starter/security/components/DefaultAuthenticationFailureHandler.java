package com.lframework.starter.security.components;

import com.lframework.common.exceptions.BaseException;
import com.lframework.common.exceptions.impl.AuthExpiredException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.DateUtil;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.security.dto.system.config.SysConfigDto;
import com.lframework.starter.security.exception.NoPermissionException;
import com.lframework.starter.security.service.system.ISysConfigService;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.ResponseUtil;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 用户认证失败处理器
 *
 * @author zmj
 */
@Slf4j
@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private ISysConfigService sysConfigService;

  @Autowired
  private IUserService userService;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException e) throws IOException, ServletException {

    if (log.isDebugEnabled()) {
      log.debug("登录错误", e);
    }

    if (e instanceof InternalAuthenticationServiceException && e
        .getCause() instanceof AuthenticationException) {
      e = (AuthenticationException) e.getCause();
    }

    BaseException ex = null;
    if (e instanceof UsernameNotFoundException) {
      ex = new DefaultClientException("用户名或密码错误！");
    } else if (e instanceof AccountExpiredException) {
      //由UserDetails.isAccountNonExpired()返回值为false时抛出
      ex = new DefaultClientException("用户已过期，无法登录！");
    } else if (e instanceof LockedException) {
      //由UserDetails.isAccountNonLocked()返回值为false时抛出
      ex = new DefaultClientException("用户已锁定，无法登录！");
    } else if (e instanceof DisabledException) {
      //由UserDetails.isEnabled()返回值为false时抛出
      ex = new DefaultClientException("用户已停用，无法登录！");
    } else if (e instanceof BadCredentialsException) {
      SysConfigDto config = sysConfigService.get();
      if (config.getAllowLock()) {
        String username = request.getParameter("username");
        String lockKey = username + "_" + DateUtil.formatDate(LocalDate.now()) + "_LOGIN_LOCK";
        long loginErrorNum = redisHandler.incr(lockKey, 1);
        redisHandler.expire(lockKey, 86400000L);
        if (loginErrorNum < config.getFailNum()) {
          ex = new DefaultClientException(
              "您已经登录失败" + loginErrorNum + "次，您还可以尝试" + (config.getFailNum() - loginErrorNum)
                  + "次！");
        } else {
          AbstractUserDetailsService userDetailsService = ApplicationUtil
              .getBean(AbstractUserDetailsService.class);
          AbstractUserDetails user = userDetailsService.findByUsername(username);
          userService.lockById(user.getId());
          redisHandler.expire(lockKey, 1L);
          // 锁定用户
          ex = new DefaultClientException("用户已锁定，无法登录！");
        }
      }
      if (ex == null) {
        ex = new DefaultClientException("用户名或密码错误！");
      }
    } else if (e instanceof CredentialsExpiredException) {
      //当UserDetails.isCredentialsNonExpired()返回值为false时抛出
      ex = new AuthExpiredException();
    } else if (e instanceof NoPermissionException) {
      ex = new DefaultClientException("用户无权限，无法登录！");
    } else {
      ex = new DefaultClientException("登录失败！");
    }

    ResponseUtil.respFailJson(response, ex);
  }
}
