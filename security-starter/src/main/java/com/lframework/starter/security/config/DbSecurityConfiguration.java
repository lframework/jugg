package com.lframework.starter.security.config;

import com.lframework.starter.security.components.DefaultUserDetailsService;
import com.lframework.starter.security.impl.DefaultMenuServiceImpl;
import com.lframework.starter.security.impl.DefaultUserServiceImpl;
import com.lframework.starter.security.impl.system.RecursionMappingServiceImpl;
import com.lframework.starter.security.service.system.IRecursionMappingService;
import com.lframework.starter.web.service.IMenuService;
import com.lframework.starter.web.service.IUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 基于DB的权限配置
 * 如果UserDetailsService不存在，则使用默认UserDetailsService
 *
 * @author zmj
 */
@Configuration
public class DbSecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService getUserDetailsService() {

        DefaultUserDetailsService userDetailsService = new DefaultUserDetailsService();
        return userDetailsService;
    }

    @Bean
    @ConditionalOnMissingBean(IMenuService.class)
    public IMenuService getMenuService() {

        DefaultMenuServiceImpl menuService = new DefaultMenuServiceImpl();
        return menuService;
    }

    @Bean
    @ConditionalOnMissingBean(IUserService.class)
    public IUserService getUserService() {

        DefaultUserServiceImpl userService = new DefaultUserServiceImpl();
        return userService;
    }

    @Bean
    @ConditionalOnMissingBean(IRecursionMappingService.class)
    public IRecursionMappingService getRecursionMappingService() {

        return new RecursionMappingServiceImpl();
    }
}
