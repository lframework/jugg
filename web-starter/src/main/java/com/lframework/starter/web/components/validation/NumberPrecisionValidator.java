package com.lframework.starter.web.components.validation;

import com.lframework.common.utils.NumberUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 小数位数校验 如果参数是null或不是Number，则不进行校验
 *
 * @author zmj
 */
public class NumberPrecisionValidator implements ConstraintValidator<IsNumberPrecision, Object> {

  private int precision;

  @Override
  public void initialize(IsNumberPrecision constraintAnnotation) {

    this.precision = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(Object charSequence,
      ConstraintValidatorContext constraintValidatorContext) {
    if (charSequence == null) {
      return true;
    }
    if (charSequence instanceof Number) {
      return NumberUtil.isNumberPrecision((Number) charSequence, this.precision);
    }

    return true;
  }
}
