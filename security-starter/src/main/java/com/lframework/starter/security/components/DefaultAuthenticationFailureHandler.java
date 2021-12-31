package com.lframework.starter.security.components;

import com.lframework.common.exceptions.BaseException;
import com.lframework.common.exceptions.impl.AuthExpiredException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.security.exception.NoPermissionException;
import com.lframework.starter.web.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户认证失败处理器
 *
 * @author zmj
 */
@Slf4j
@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {

        if (log.isDebugEnabled()) {
            log.debug("登录错误", e);
        }

        if (e instanceof InternalAuthenticationServiceException && e.getCause() instanceof AuthenticationException) {
            e = (AuthenticationException) e.getCause();
        }

        BaseException ex;
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
            ex = new DefaultClientException("用户名或密码错误！");
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
