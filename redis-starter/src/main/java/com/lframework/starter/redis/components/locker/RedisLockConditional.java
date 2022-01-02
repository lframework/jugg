package com.lframework.starter.redis.components.locker;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RedisLockConditional implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        String lockerType = context.getEnvironment().getProperty("locker-type");
        return "redis".equalsIgnoreCase(lockerType);
    }
}
