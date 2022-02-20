package com.lframework.starter.web.components.validation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TypeMismatch {

    String message() default "数据格式有误！";
}
