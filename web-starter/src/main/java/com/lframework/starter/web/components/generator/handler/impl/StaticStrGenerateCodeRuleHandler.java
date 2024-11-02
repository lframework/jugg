package com.lframework.starter.web.components.generator.handler.impl;

import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.web.components.generator.handler.GenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.components.generator.rule.impl.StaticStrGenerateCodeRule;
import com.lframework.starter.web.utils.JsonUtil;

public class StaticStrGenerateCodeRuleHandler implements
    GenerateCodeRuleHandler<StaticStrGenerateCodeRule> {

  @Override
  public boolean match(Integer ruleType) {
    return ruleType != null && ruleType == 6;
  }

  @Override
  public boolean match(GenerateCodeRule rule) {
    return rule instanceof StaticStrGenerateCodeRule;
  }

  @Override
  public String generate(StaticStrGenerateCodeRule rule) {

    return rule.getVal();
  }

  @Override
  public String generateSimple(StaticStrGenerateCodeRule rule) {
    return generate(rule);
  }

  @Override
  public StaticStrGenerateCodeRule parseRule(String json) {
    StaticStrGenerateCodeRule rule = JsonUtil.parseObject(json, StaticStrGenerateCodeRule.class);

    Assert.notNull(rule.getVal());

    return rule;
  }
}
