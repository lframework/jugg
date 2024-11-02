package com.lframework.starter.web.components.generator.handler.impl;

import com.lframework.starter.common.utils.IdWorker;
import com.lframework.starter.web.components.generator.handler.GenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.components.generator.rule.impl.SnowFlakeGenerateCodeRule;
import org.springframework.beans.factory.annotation.Autowired;

public class SnowFlakeGenerateCodeRuleHandler implements
    GenerateCodeRuleHandler<SnowFlakeGenerateCodeRule> {

  @Autowired
  private IdWorker idWorker;

  @Override
  public boolean match(Integer ruleType) {
    return ruleType != null && ruleType == 5;
  }

  @Override
  public boolean match(GenerateCodeRule rule) {
    return rule instanceof SnowFlakeGenerateCodeRule;
  }

  @Override
  public String generate(SnowFlakeGenerateCodeRule rule) {
    return idWorker.nextIdStr();
  }

  @Override
  public String generateExample(SnowFlakeGenerateCodeRule rule) {
    return generate(rule);
  }

  @Override
  public SnowFlakeGenerateCodeRule parseRule(String json) {
    return new SnowFlakeGenerateCodeRule();
  }
}
