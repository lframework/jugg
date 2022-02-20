package com.lframework.starter.web.components.validation;

import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.enums.BaseEnum;
import com.lframework.starter.web.utils.EnumUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;

/**
 * 正则表达式校验
 * 如果参数是null or empty 则通过校验
 *
 * @author zmj
 */
public class PatternValidator implements ConstraintValidator<Pattern, Object> {

    /**
     * 正则表达式
     */
    private String regExp;

    @Override
    public void initialize(Pattern constraintAnnotation) {

        this.regExp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        return value == null || (value instanceof CharSequence && StringUtil.isEmpty((CharSequence) value)) || RegUtil.isMatch(java.util.regex.Pattern.compile(this.regExp), StringUtil.toString(value));
    }
}
