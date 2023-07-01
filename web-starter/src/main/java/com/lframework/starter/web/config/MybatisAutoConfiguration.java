package com.lframework.starter.web.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lframework.starter.web.handlers.DefaultBaseEntityFillHandler;
import com.lframework.starter.web.injectors.MybatisPlusUpdateAllColumnInjector;
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
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
    return interceptor;
  }

  @Bean
  public MybatisPlusUpdateAllColumnInjector mybatisPlusUpdateAllColumnInjector() {
    return new MybatisPlusUpdateAllColumnInjector();
  }
}
