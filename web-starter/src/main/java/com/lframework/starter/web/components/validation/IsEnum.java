package com.lframework.starter.web.components.validation;

import com.lframework.starter.web.enums.BaseEnum;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 校验枚举类型
 *
 * @author zmj
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface IsEnum {

  String message();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<? extends BaseEnum<? extends Serializable>> enumClass();
}
