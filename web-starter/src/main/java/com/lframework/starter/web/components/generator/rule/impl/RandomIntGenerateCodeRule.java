package com.lframework.starter.web.components.generator.rule.impl;

import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import java.io.Serializable;
import lombok.Data;

/**
 * 随机整数生成规则
 */
@Data
public class RandomIntGenerateCodeRule extends CustomRandomStrGenerateCodeRule implements
    GenerateCodeRule, Serializable {

  private static final long serialVersionUID = 1L;

  public RandomIntGenerateCodeRule() {
    setPool("0123456789");
  }

  /**
   * 长度
   */
  private Integer len;
}
