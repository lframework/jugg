package com.lframework.starter.web.components.validation;

import com.lframework.starter.web.enums.BaseEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.io.Serializable;
import java.lang.annotation.*;

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
