package com.lframework.starter.web.components.validation;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 上传文件url校验 如果参数是null 则通过校验
 *
 * @author zmj
 */
public class UploadUrlValidator implements ConstraintValidator<UploadUrl, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    return StringUtil.isEmpty(value) || RegUtil.isMatch(PatternPool.PATTERN_HTTP_URL, value);
  }
}
