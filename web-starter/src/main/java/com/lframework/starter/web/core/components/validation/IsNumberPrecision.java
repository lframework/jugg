package com.lframework.starter.web.core.components.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 校验小数位数
 *
 * @author zmj
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NumberPrecisionValidator.class)
public @interface IsNumberPrecision {

  String message();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int value();
}
