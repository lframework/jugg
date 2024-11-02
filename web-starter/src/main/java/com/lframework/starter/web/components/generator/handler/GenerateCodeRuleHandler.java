package com.lframework.starter.web.components.generator.handler;

import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;

public interface GenerateCodeRuleHandler<T extends GenerateCodeRule> {

  /**
   * 根据规则类型匹配
   *
   * @param ruleType
   * @return
   */
  boolean match(Integer ruleType);

  /**
   * 根据规则匹配
   * @param rule
   * @return
   */
  boolean match(GenerateCodeRule rule);

  /**
   * 生成单号
   *
   * @param rule 规则数据
   * @return
   */
  String generate(T rule);

  /**
   * 生成示例单号
   *
   * @param rule
   * @return
   */
  String generateSimple(T rule);

  /**
   * 解析规则
   *
   * @param json
   * @return
   */
  T parseRule(String json);
}
