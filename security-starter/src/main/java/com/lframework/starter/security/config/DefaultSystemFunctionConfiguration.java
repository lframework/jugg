package com.lframework.starter.security.config;

import com.lframework.starter.security.impl.system.DefaultSysConfigServiceImpl;
import com.lframework.starter.security.impl.system.DefaultSysDeptServiceImpl;
import com.lframework.starter.security.impl.system.DefaultSysMenuServiceImpl;
import com.lframework.starter.security.impl.system.DefaultSysPositionServiceImpl;
import com.lframework.starter.security.impl.system.DefaultSysRoleMenuServiceImpl;
import com.lframework.starter.security.impl.system.DefaultSysRoleServiceImpl;
import com.lframework.starter.security.impl.system.DefaultSysUserDeptServiceImpl;
import com.lframework.starter.security.impl.system.DefaultSysUserPositionServiceImpl;
import com.lframework.starter.security.impl.system.DefaultSysUserRoleServiceImpl;
import com.lframework.starter.security.impl.system.DefaultSysUserServiceImpl;
import com.lframework.starter.security.service.system.ISysConfigService;
import com.lframework.starter.security.service.system.ISysDeptService;
import com.lframework.starter.security.service.system.ISysMenuService;
import com.lframework.starter.security.service.system.ISysPositionService;
import com.lframework.starter.security.service.system.ISysRoleMenuService;
import com.lframework.starter.security.service.system.ISysRoleService;
import com.lframework.starter.security.service.system.ISysUserDeptService;
import com.lframework.starter.security.service.system.ISysUserPositionService;
import com.lframework.starter.security.service.system.ISysUserRoleService;
import com.lframework.starter.security.service.system.ISysUserService;
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

  @Bean
  @ConditionalOnMissingBean(ISysConfigService.class)
  public ISysConfigService getSysConfigService() {

    return new DefaultSysConfigServiceImpl();
  }
}
