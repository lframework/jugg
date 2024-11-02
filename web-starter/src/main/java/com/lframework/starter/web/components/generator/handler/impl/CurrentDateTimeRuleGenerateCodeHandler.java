package com.lframework.starter.web.components.generator.handler.impl;

import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.generator.handler.GenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.components.generator.rule.impl.CurrentDateTimeGenerateCodeRule;
import com.lframework.starter.web.utils.JsonUtil;
import java.time.LocalDateTime;

public class CurrentDateTimeRuleGenerateCodeHandler implements
    GenerateCodeRuleHandler<CurrentDateTimeGenerateCodeRule> {

  @Override
  public boolean match(Integer ruleType) {
    return ruleType != null && ruleType == 1;
  }

  @Override
  public boolean match(GenerateCodeRule rule) {
    return rule instanceof CurrentDateTimeGenerateCodeRule;
  }

  @Override
  public String generate(CurrentDateTimeGenerateCodeRule rule) {
    return DateUtil.formatDateTime(LocalDateTime.now(), rule.getPattern());
  }

  @Override
  public String generateExample(CurrentDateTimeGenerateCodeRule rule) {
    return generate(rule);
  }

  @Override
  public CurrentDateTimeGenerateCodeRule parseRule(String json) {
    CurrentDateTimeGenerateCodeRule rule = JsonUtil.parseObject(json,
        CurrentDateTimeGenerateCodeRule.class);
    if (StringUtil.isBlank(rule.getPattern())) {
      rule.setPattern("yyyyMMddHHmmss");
    }
    return rule;
  }
}
