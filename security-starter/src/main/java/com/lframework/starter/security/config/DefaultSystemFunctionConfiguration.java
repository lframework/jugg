package com.lframework.starter.security.config;

import com.lframework.starter.security.impl.system.*;
import com.lframework.starter.security.service.system.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
public class DefaultSystemFunctionConfiguration {

    @Bean
    @ConditionalOnMissingBean(ISysDeptService.class)
    public ISysDeptService getSysDeptService() {

        return new DefaultSysDeptServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ISysMenuService.class)
    public ISysMenuService getSysMenuService() {

        return new DefaultSysMenuServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ISysPositionService.class)
    public ISysPositionService getSysPositionService() {

        return new DefaultSysPositionServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ISysRoleMenuService.class)
    public ISysRoleMenuService getSysRoleMenuService() {

        return new DefaultSysRoleMenuServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ISysUserRoleService.class)
    public ISysUserRoleService getSysUserRoleService() {

        return new DefaultSysUserRoleServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ISysUserService.class)
    public ISysUserService getSysUserService() {

        return new DefaultSysUserServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ISysRoleService.class)
    public ISysRoleService getSysRoleService() {

        return new DefaultSysRoleServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ISysUserPositionService.class)
    public ISysUserPositionService getSysUserPositionService() {

        return new DefaultSysUserPositionServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ISysUserDeptService.class)
    public ISysUserDeptService getSysUserDeptService() {

        return new DefaultSysUserDeptServiceImpl();
    }
}
