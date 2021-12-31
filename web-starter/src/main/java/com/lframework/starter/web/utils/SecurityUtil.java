package com.lframework.starter.web.utils;

import com.lframework.starter.web.components.security.AbstractUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;

/**
 * Spring Security工具类
 *
 * @author zmj
 */
@Slf4j
public class SecurityUtil {

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static AbstractUserDetails getCurrentUser() {

        Authentication authentication = getAuthentication();

        return getCurrentUser(authentication);
    }

    public static AbstractUserDetails getCurrentUser(Authentication authentication) {

        if (authentication == null) {
            return null;
        }

        Object principal  = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }

        if (principal instanceof AbstractUserDetails) {
            return (AbstractUserDetails) principal;
        }

        return null;
    }

    public static Authentication getAuthentication() {

        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    /**
     * 手动退出登录
     */
    public static void logout() {

        try {
            RequestUtil.getRequest().logout();
        } catch (ServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
