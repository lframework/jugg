package com.lframework.starter.web.config;

import com.lframework.starter.common.locker.LockBuilder;
import com.lframework.starter.web.annotations.locker.EnableLock;
import com.lframework.starter.web.annotations.locker.LockType;
import com.lframework.starter.web.components.locker.DefaultLockBuilder;
import com.lframework.starter.web.components.redis.locker.RedisLockBuilder;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class LockAutoConfiguration implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata
            .getAnnotationAttributes(EnableLock.class.getName());

        LockType type = (LockType) annotationAttributes.get("type");
        if (type == LockType.REDIS) {
            return new String[]{RedisLockConfiguration.class.getName()};
        }

        return new String[]{LockConfiguration.class.getName()};
    }

    public static class LockConfiguration {

        @Bean
        public LockBuilder lockBuilder() {
            return new DefaultLockBuilder();
        }
    }

    public static class RedisLockConfiguration {

        @Bean
        public LockBuilder lockBuilder() {
            return new RedisLockBuilder();
        }
    }
}
