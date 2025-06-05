package com.lframework.starter.web.core.components.generator.rule.impl;

import com.lframework.starter.web.core.components.generator.rule.AbstractGenerateCodeRule;
import com.lframework.starter.web.core.components.generator.rule.GenerateCodeRule;
import java.io.Serializable;
import lombok.Data;

/**
 * 静态字符串生成规则
 */
@Data
public class StaticStrGenerateCodeRule extends AbstractGenerateCodeRule implements GenerateCodeRule, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 字符串
   */
  private String val;
}
