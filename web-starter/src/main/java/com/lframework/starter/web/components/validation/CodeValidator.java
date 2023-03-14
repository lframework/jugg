package com.lframework.starter.web.components.validation;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 编号校验 如果参数是null或empty 则通过校验
 *
 * @author zmj
 */
public class CodeValidator implements ConstraintValidator<IsCode, CharSequence> {

  @Override
  public boolean isValid(CharSequence charSequence,
      ConstraintValidatorContext constraintValidatorContext) {

    return StringUtil.isEmpty(charSequence) || RegUtil.isMatch(PatternPool.PATTERN_CODE,
        charSequence.toString());
  }
}
