package com.lframework.starter.web.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lframework.starter.web.core.components.permission.DataPermissionHandlerImpl;
import com.lframework.starter.web.core.handlers.DefaultBaseEntityFillHandler;
import com.lframework.starter.web.core.injectors.MybatisPlusUpdateAllColumnInjector;
import com.lframework.starter.web.core.interceptors.CustomSortInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(MetaObjectHandler.class)
  public MetaObjectHandler getMetaObjectHandler() {

    return new DefaultBaseEntityFillHandler();
  }

  @Bean
  @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(
        new DataPermissionInterceptor(new DataPermissionHandlerImpl()));
    interceptor.addInnerInterceptor(new CustomSortInterceptor());
    interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    return interceptor;
  }

  @Bean
  public MybatisPlusUpdateAllColumnInjector mybatisPlusUpdateAllColumnInjector() {
    return new MybatisPlusUpdateAllColumnInjector();
  }
}
