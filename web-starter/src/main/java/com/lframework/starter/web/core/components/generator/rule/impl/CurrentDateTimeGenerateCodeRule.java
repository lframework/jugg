package com.lframework.starter.web.core.components.generator.rule.impl;

import com.lframework.starter.web.core.components.generator.rule.AbstractGenerateCodeRule;
import com.lframework.starter.web.core.components.generator.rule.GenerateCodeRule;
import java.io.Serializable;
import lombok.Data;

/**
 * 当前时间生成规则
 */
@Data
public class CurrentDateTimeGenerateCodeRule extends AbstractGenerateCodeRule implements GenerateCodeRule, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 时间格式
   */
  private String pattern;
}
