package com.lframework.starter.web.core.components.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * JSON类型
 *
 * @author zmj
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = JsonValidator.class)
public @interface IsJson {

  Class<?>[] groups() default {};

  String message();

  Class<? extends Payload>[] payload() default {};
}
