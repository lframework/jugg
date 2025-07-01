package com.lframework.starter.web.core.annotations.openapi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否为开放API
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenApi {

  /**
   * 是否验证签名
   *
   * @return
   */
  boolean sign() default false;
}
