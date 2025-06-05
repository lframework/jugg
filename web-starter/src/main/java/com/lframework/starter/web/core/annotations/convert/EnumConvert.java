package com.lframework.starter.web.core.annotations.convert;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dto转Bo时，是否为Enum字段
 *
 * 只支持code是字符串或数字
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumConvert {

}
