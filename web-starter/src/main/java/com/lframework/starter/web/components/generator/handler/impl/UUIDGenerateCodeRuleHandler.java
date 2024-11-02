package com.lframework.starter.web.components.generator.handler.impl;

import com.lframework.starter.web.components.generator.handler.GenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.components.generator.rule.impl.UUIDGenerateCodeRule;
import com.lframework.starter.web.utils.IdUtil;

public class UUIDGenerateCodeRuleHandler implements GenerateCodeRuleHandler<UUIDGenerateCodeRule> {

  @Override
  public boolean match(Integer ruleType) {
    return ruleType != null && ruleType == 7;
  }

  @Override
  public boolean match(GenerateCodeRule rule) {
    return rule instanceof UUIDGenerateCodeRule;
  }

  @Override
  public String generate(UUIDGenerateCodeRule rule) {
    return IdUtil.getUUID();
  }

  @Override
  public String generateExample(UUIDGenerateCodeRule rule) {
    return generate(rule);
  }

  @Override
  public UUIDGenerateCodeRule parseRule(String json) {
    return new UUIDGenerateCodeRule();
  }
}
