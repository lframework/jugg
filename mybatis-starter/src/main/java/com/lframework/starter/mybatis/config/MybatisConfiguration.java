package com.lframework.starter.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lframework.starter.mybatis.handlers.DefaultBaseEntityFillHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfiguration {

    @Bean
    @ConditionalOnMissingBean(MetaObjectHandler.class)
    public MetaObjectHandler getMetaObjectHandler() {

        return new DefaultBaseEntityFillHandler();
    }
}
