package com.lframework.starter.web.components.validation;

import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.web.enums.BaseEnum;
import com.lframework.starter.web.utils.EnumUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;

/**
 * 枚举校验
 * 如果参数是null 则通过校验
 *
 * @author zmj
 */
public class EnumValidator implements ConstraintValidator<IsEnum, Serializable> {

    private Class<? extends BaseEnum<? extends Serializable>> enumsClass;

    @Override
    public void initialize(IsEnum constraintAnnotation) {

        this.enumsClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Serializable value, ConstraintValidatorContext context) {

        return ObjectUtil.isNull(value) || ObjectUtil.isNotNull(EnumUtil.getByCode(enumsClass, value));
    }
}
