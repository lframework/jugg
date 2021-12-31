package com.lframework.starter.security.components;

import com.lframework.starter.security.event.LogoutEvent;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户退出登录处理器
 *
 * @author zmj
 */
public class DefaultLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        AbstractUserDetails currentUser = SecurityUtil.getCurrentUser(authentication);

        if (currentUser != null) {
            ApplicationUtil.publishEvent(new LogoutEvent(this, currentUser));
        }
    }
}
