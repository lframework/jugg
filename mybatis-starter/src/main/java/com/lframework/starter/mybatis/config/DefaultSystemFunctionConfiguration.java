package com.lframework.starter.mybatis.config;

import com.lframework.starter.mybatis.impl.AbstractMenuServiceImpl;
import com.lframework.starter.mybatis.impl.DefaultMenuServiceImpl;
import com.lframework.starter.mybatis.impl.DefaultUserDetailsService;
import com.lframework.starter.mybatis.impl.DefaultUserServiceImpl;
import com.lframework.starter.mybatis.impl.message.TodoTaskServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysConfigServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysDeptServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysMenuServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysPositionServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysRoleMenuServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysRoleServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysUserDeptServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysUserPositionServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysUserRoleServiceImpl;
import com.lframework.starter.mybatis.impl.system.DefaultSysUserServiceImpl;
import com.lframework.starter.mybatis.impl.system.RecursionMappingServiceImpl;
import com.lframework.starter.mybatis.service.IMenuService;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.mybatis.service.message.ITodoTaskService;
import com.lframework.starter.mybatis.service.system.IRecursionMappingService;
import com.lframework.starter.mybatis.service.system.ISysConfigService;
import com.lframework.starter.mybatis.service.system.ISysDeptService;
import com.lframework.starter.mybatis.service.system.ISysMenuService;
import com.lframework.starter.mybatis.service.system.ISysPositionService;
import com.lframework.starter.mybatis.service.system.ISysRoleMenuService;
import com.lframework.starter.mybatis.service.system.ISysRoleService;
import com.lframework.starter.mybatis.service.system.ISysUserDeptService;
import com.lframework.starter.mybatis.service.system.ISysUserPositionService;
import com.lframework.starter.mybatis.service.system.ISysUserRoleService;
import com.lframework.starter.mybatis.service.system.ISysUserService;
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

  @Bean
  @ConditionalOnMissingBean(IMenuService.class)
  public IMenuService getMenuService() {

    AbstractMenuServiceImpl menuService = new DefaultMenuServiceImpl();
    return menuService;
  }

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

  @Bean
  @ConditionalOnMissingBean(ISysConfigService.class)
  public ISysConfigService getSysConfigService() {

    return new DefaultSysConfigServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(ITodoTaskService.class)
  public ITodoTaskService getTodoTaskService() {

    return new TodoTaskServiceImpl();
  }
}
