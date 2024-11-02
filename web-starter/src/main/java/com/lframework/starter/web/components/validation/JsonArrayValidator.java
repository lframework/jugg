package com.lframework.starter.web.components.validation;

import cn.hutool.json.JSONUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * JSONArray校验 如果参数是null 则通过校验
 *
 * @author zmj
 */
public class JsonArrayValidator implements ConstraintValidator<IsJsonArray, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    return value == null || JSONUtil.isJsonArray(value);
  }
}
