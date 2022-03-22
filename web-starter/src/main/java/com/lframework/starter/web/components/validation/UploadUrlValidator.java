package com.lframework.starter.web.components.validation;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.enums.BaseEnum;
import com.lframework.starter.web.utils.ApplicationUtil;
import java.io.Serializable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 上传文件url校验 如果参数是null 则通过校验
 *
 * @author zmj
 */
public class UploadUrlValidator implements ConstraintValidator<UploadUrl, String> {

  private Class<? extends BaseEnum<? extends Serializable>> enumsClass;

  private String url;

  @Override
  public void initialize(UploadUrl constraintAnnotation) {
    String domain = ApplicationUtil.getProperty("upload.domain");
    if (domain.endsWith("/")) {
      domain = domain.substring(0, domain.length() - 1);
    }
    String baseUrl = ApplicationUtil.getProperty("upload.url");
    if (!baseUrl.startsWith("/")) {
      baseUrl = "/" + baseUrl;
    }

    this.url = domain + baseUrl;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    return StringUtil.isEmpty(value) || value.startsWith(value);
  }
}
