package com.lframework.starter.web.annotations.locker;

import com.lframework.starter.web.config.LockAutoConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LockAutoConfiguration.class)
@Documented
public @interface EnableLock {

    /**
     * 类型：redis、default
     * <p>
     * redis：基于Redis的分布式锁
     * <p>
     * default：基于JUC的java锁
     *
     * @return
     */
    LockType type() default LockType.DEFAULT;
}
