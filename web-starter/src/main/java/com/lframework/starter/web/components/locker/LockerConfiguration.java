package com.lframework.starter.web.components.locker;

import com.lframework.common.locker.LockBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockerConfiguration {

    @Bean
    @ConditionalOnMissingBean(LockBuilder.class)
    public LockBuilder getLockBuilder() {

        return new DefaultLockBuilder();
    }
}
