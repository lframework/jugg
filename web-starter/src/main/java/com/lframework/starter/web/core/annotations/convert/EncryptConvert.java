package com.lframework.starter.web.core.annotations.convert;

import com.lframework.starter.web.core.annotations.constants.EncryType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dto转Bo时，是否为脱敏字段 只对字符串有效
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptConvert {

  /**
   * 类型
   *
   * @return
   */
  EncryType type() default EncryType.AUTO;
}
