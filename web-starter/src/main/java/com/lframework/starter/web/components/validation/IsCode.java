package com.lframework.starter.web.components.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 编号类型
 *
 * @author zmj
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CodeValidator.class)
public @interface IsCode {

  Class<?>[] groups() default {};

  String message() default "编号必须由字母、数字、“-_.”组成，长度不能超过20位";

  Class<? extends Payload>[] payload() default {};
}
