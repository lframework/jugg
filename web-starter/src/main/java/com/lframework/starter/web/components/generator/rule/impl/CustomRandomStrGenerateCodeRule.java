package com.lframework.starter.web.components.generator.rule.impl;

import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import java.io.Serializable;
import lombok.Data;

/**
 * 自定义随机字符串生成规则
 */
@Data
public class CustomRandomStrGenerateCodeRule implements GenerateCodeRule, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 字符池
   */
  private String pool;

  /**
   * 长度
   */
  private Integer len;
}
