package com.lframework.starter.mybatis.config;

import com.lframework.starter.mybatis.impl.AbstractMenuServiceImpl;
import com.lframework.starter.mybatis.impl.DefaultMenuServiceImpl;
import com.lframework.starter.mybatis.impl.DefaultUserDetailsService;
import com.lframework.starter.mybatis.impl.DefaultUserServiceImpl;
import com.lframework.starter.mybatis.impl.message.TodoTaskServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysDeptServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysMenuServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysPositionServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysRoleMenuServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysRoleServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysUserDeptServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysUserPositionServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysUserRoleServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysUserServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysUserTelephoneServiceImpl;
import com.lframework.starter.mybatis.impl.system.RecursionMappingServiceImpl;
import com.lframework.starter.mybatis.service.MenuService;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.mybatis.service.message.TodoTaskService;
import com.lframework.starter.mybatis.service.system.RecursionMappingService;
import com.lframework.starter.mybatis.service.system.SysDeptService;
import com.lframework.starter.mybatis.service.system.SysMenuService;
import com.lframework.starter.mybatis.service.system.SysPositionService;
import com.lframework.starter.mybatis.service.system.SysRoleMenuService;
import com.lframework.starter.mybatis.service.system.SysRoleService;
import com.lframework.starter.mybatis.service.system.SysUserDeptService;
import com.lframework.starter.mybatis.service.system.SysUserPositionService;
import com.lframework.starter.mybatis.service.system.SysUserRoleService;
import com.lframework.starter.mybatis.service.system.SysUserService;
import com.lframework.starter.mybatis.service.system.SysUserTelephoneService;
import com.lframework.starter.web.components.security.UserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultSystemFunctionConfiguration {

  @Bean
  @ConditionalOnMissingBean(UserDetailsService.class)
  public UserDetailsService getUserDetailsService() {

    DefaultUserDetailsService userDetailsService = new DefaultUserDetailsService();
    return userDetailsService;
  }

  @Bean
  @ConditionalOnMissingBean(UserService.class)
  public UserService getUserService() {

    DefaultUserServiceImpl userService = new DefaultUserServiceImpl();
    return userService;
  }

  @Bean
  @ConditionalOnMissingBean(RecursionMappingService.class)
  public RecursionMappingService getRecursionMappingService() {

    return new RecursionMappingServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(MenuService.class)
  public MenuService getMenuService() {

    AbstractMenuServiceImpl menuService = new DefaultMenuServiceImpl();
    return menuService;
  }

  @Bean
  @ConditionalOnMissingBean(SysDeptService.class)
  public SysDeptService getSysDeptService() {

    return new DefaultSysDeptServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(SysMenuService.class)
  public SysMenuService getSysMenuService() {

    return new DefaultSysMenuServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(SysPositionService.class)
  public SysPositionService getSysPositionService() {

    return new DefaultSysPositionServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(SysRoleMenuService.class)
  public SysRoleMenuService getSysRoleMenuService() {

    return new DefaultSysRoleMenuServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(SysUserRoleService.class)
  public SysUserRoleService getSysUserRoleService() {

    return new DefaultSysUserRoleServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(SysUserService.class)
  public SysUserService getSysUserService() {

    return new DefaultSysUserServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(SysRoleService.class)
  public SysRoleService getSysRoleService() {

    return new DefaultSysRoleServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(SysUserPositionService.class)
  public SysUserPositionService getSysUserPositionService() {

    return new DefaultSysUserPositionServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(SysUserDeptService.class)
  public SysUserDeptService getSysUserDeptService() {

    return new DefaultSysUserDeptServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(TodoTaskService.class)
  public TodoTaskService getTodoTaskService() {

    return new TodoTaskServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(SysUserTelephoneService.class)
  public SysUserTelephoneService getSysUserTelephoneService() {

    return new DefaultSysUserTelephoneServiceImpl();
  }
}
