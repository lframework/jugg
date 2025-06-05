package com.lframework.starter.web.core.components.validation;

import cn.hutool.json.JSONUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * JSON校验 如果参数是null 则通过校验
 *
 * @author zmj
 */
public class JsonValidator implements ConstraintValidator<IsJson, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    return value == null || JSONUtil.isJson(value);
  }
}
